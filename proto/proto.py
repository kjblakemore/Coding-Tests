import numpy as np
import pandas as pd
from struct import *

# type field
DEBIT = 0x00
CREDIT = 0x01
START_AUTO_PAY = 0x02
END_AUTO_PAY = 0x03

# Read txnlog file into a dataframe, by:
#   1. Read header, extracting the count of records
#   2. Create an empty dataframe with type, user and amount columns
#   3. Read each record, storing the type, user and amount as a row in the data frame
f = open('txnlog.dat', 'rb')

header = f.read(9)
count = int.from_bytes(header[5:9], byteorder = 'big', signed = False)

record_array = np.empty((count,), dtype=[('type', np.uint8), ('user', np.uint64), ('amount', np.float64)])
records = pd.DataFrame(record_array)

for i in range(count):
  record = f.read(13)
  record_type = record[0]
  user = int.from_bytes(record[5:13], byteorder = 'big', signed = False) 

  if(record_type in (DEBIT, CREDIT)):
    amount_field = f.read(8)
    amount = unpack('!d', amount_field)[0]
  else:
    amount = 0.0

  records.loc[i] = [record_type, user, amount]

# What is the total amount in dollars of debits?
debit_sum = records[records.type == DEBIT]['amount'].sum()
print('The total amount in dollars of debits is ${:,.2f}'.format(debit_sum))

# What is the total amount in dollars of credits?
credit_sum = records[records.type == CREDIT]['amount'].sum()
print('The total amount in dollars of credits is ${:,.2f}'.format(credit_sum))

# How many autopays were started?
autopays_started = len(records[records.type == START_AUTO_PAY])
print('The number of autopays started was: ', autopays_started)

# How many autopays were ended?
autopays_ended = len(records[records.type == END_AUTO_PAY])
print('The number of autopays ended was: ', autopays_ended)

# What is balance of user ID 2456938384156277127?
credits = records[(records.user == 2456938384156277127) & (records.type == CREDIT)]['amount'].sum()
debits = records[(records.user == 2456938384156277127) & (records.type == DEBIT)]['amount'].sum()
balance = credits - debits
print('The balance for user ID 2456938384156277127 is ${:,.2f}'.format(balance))