import pandas as pd

# Read page views data set and assign column names
page_views = pd.read_csv('page-views.csv', names = ['timestamp', 'path', 'referrer', 'cid'])

#
# Question 1: What are the top 5 most frequently issued queries?
#
# 1. Create a new column with the query string.
# 2. Group by lower-cased query
# 3. Count size of each group then order groups by size and select top 5.
#
page_views['query'] = page_views['path'].str.lower().str.extract('/search\?q=(\S.*)', expand=True)
queries = page_views.groupby('query', sort=False)
top5_queries= queries.size().nlargest(5)
print('\nTop 5 Queries: ')
print(top5_queries)

#
# Question 2: What are the top 5 queries in terms of the total number of results clicked?
#
# Same as Question 1, except overwite referrer field with search term.
#
page_views['referrer'] = page_views['referrer'].str.lower().str.extract('/search\?q=(\S.*)', expand=True)
referrers = page_views.groupby('referrer', sort=False)
top5_referrers= referrers.size().nlargest(5)
print('\nTop 5 Referrers: ')
print(top5_referrers)

#
# Question 3: What is the average length of a search session?
#
# 1. Create groups indexed by (cid, referrer query)
# 2. Calculate the average length of these groups.
#
cid_referrers = page_views.groupby(['cid', 'referrer'], sort=False)
avg_search_length = cid_referrers.size().sum() / cid_referrers.ngroups
print('\nAverage Search Length: ', avg_search_length)

#
# Extra Question: What is the average time of a search session?
#
# Copy referrer queries to the query column, then group by [cid, query] and calculate the average
# time between the start and end of a search session.
#
pd.options.mode.chained_assignment = None # Disable warning for chained assignment
mask = (page_views['query'].isnull())
page_views['query'][mask] = page_views['referrer'][mask]
cid_queries = page_views.groupby(['cid', 'query'], sort=False)
avg_search_time = cid_queries['timestamp'].max().sub(cid_queries['timestamp'].min()).sum() / cid_queries.ngroups
print('\nAverage Search Time: ', avg_search_time, ' seconds')