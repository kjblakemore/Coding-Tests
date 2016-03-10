import sys
import csv
import numpy as np
from scipy.stats import rankdata
from itertools import groupby, izip

# Calculate GC content of a 20 base target
def gc_content(target):
	g = float(target.count('G'))
	c = float(target.count('C'))
	a = float(target.count('A'))
	t = float(target.count('T'))
	return (g + c)/(a + t + g + c)

# Read annotation file, saving gene coordinates
gene_coords = []
with open('codingTaskAnnotation.gff3', 'r') as tsvfile:
	annotation_reader = csv.reader(tsvfile, delimiter='\t')
	next(annotation_reader)				# Skip gss verion id
	next(annotation_reader)				# Skip sequence identifier

	for row in annotation_reader:
		if (row[2] == 'gene'):
			gene_coords.append((int(row[3]), int(row[4])))

# Read base pair sequence file, storing in list of (loc, string) pairs.
# Each string consists of a 20 base pair target, followed by a 3 base pair PAM, 
# plus 2 additional unused base pairs.
with open('codingTaskSequence.fa', 'r') as fafile:
	fafile.readline()						# Skip sequence id
	sequence = fafile.read().splitlines()	# The entire sequence
	subseq_length = len(sequence[0])

	locs = range(1, (len(sequence)-1) * subseq_length, subseq_length)
	subseqs = zip(locs, sequence)

# Filter, keeping
# 1. 23 base pair sequences that end in 'NGG', where N can be any base.
# 2. Targets that have between 20% and 80% GC content
# 3. Targets without ATG motifs
# 4. Targets without homopolymers of more than 4 in a row
#    e.g., ACGATCCCCCGACGTGATCGAGG, has 5 C's.
filtered_subseqs = []
for (l,s) in subseqs:
	target = s[:40]
	pam = s[40:46]
	
	if pam[4:6] != 'GG': continue

	gc = gc_content(target)
	if (gc <= .2 or  gc >= .8):  continue

	if 'ATG' in target: continue

	homopolymers = [list(g) for k,g in groupby(target)]
	if max(len(hp) for hp in homopolymers) <= 4:
		filtered_subseqs.append((l,s))

# If no sequences left after filtering, exit
if not filtered_subseqs:
	sys.exit('No qualifying sequences')

# Score filtered targets based on 
# 	1. a G or C as the first nucleotide of PAM.
# 	2. Closeness to 45% GC content in target.
# 	3. Specifity of seed region (nucleotides 13-20).  Specificity refers to 
#	   uniqueness of the 8-mer seed region in the entire sequence.
#
# The total score is calculated as the sum of the ranks of the 3 subscores.
# Tied values are given their average rank (default behavior of rankdata).

# 1. Check for G or C in first nucleotide of PAM
subscores = [1.0 if s[40] in ['G', 'C'] else 0.0 for (l,s) in filtered_subseqs]
scores = rankdata(np.asarray(subscores))

# 2. Add rank of closeness to 45% GC content.
subscores = []
for (l, s) in filtered_subseqs:
	subscores.append(-abs(gc_content(s[:40]) - .45))
scores += rankdata(np.asarray(subscores))

# 3. Add rank of seed region specifity.  First count occurrences of seed region for each
# subsequence, then rank with low occurrences having highest rank.
subscores = [-sum([seq.count(s[12:20]) for seq in sequence]) for (l, s) in filtered_subseqs]
scores += rankdata(np.asarray(subscores))

# Print Results - target sequence, PAM sequence, score and whether it hits a gene from the
# annotation file.
for (l, s), score in izip(filtered_subseqs, scores.tolist()):

	hits_gene = False
	for (l0, l1) in gene_coords:
		if l >= l0 and l <= l1: hits_gene = True; break
		
	print 'target: ', s[:40], ' PAM: ', s[40:46], ' score: ', score, 'hit: ', hits_gene

