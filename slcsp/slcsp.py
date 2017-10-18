import pandas as pd

PLANS_FILE = 'plans.csv'
ZIPS_FILE = 'zips.csv'
SLCSP_FILE = 'slcsp.csv'

# Read csv files
plans = pd.read_csv(PLANS_FILE)
zips = pd.read_csv(ZIPS_FILE, dtype = {'zipcode': int}) # dtype to handle leading zeros
slcsp = pd.read_csv(SLCSP_FILE)

# Create a dictionary of unique zip -> (state, rate_area)
#   1) Remove unneeded columns, then drop duplicate (zip, state, rate_area)
#   2) Remove ambiguous entries, i.e., zips that map to multiple (state, rate_area)
#   4) Convert dataframe to dictionary of zip -> (state, rate_area)
zips_dict = zips.drop(['county_code', 'name'], 1).drop_duplicates() \
              .groupby('zipcode').filter(lambda x: len(x) == 1) \
              .set_index('zipcode').T.to_dict('list')     

# Create a 'map' of (state, rate_area) -> rate
#   1) Remove all non-silver plans, then remove unneeded columns.
#   2) Sort in ascending order by (state, rate_area, rate).
#   3) Group by (state, rate_area), keeping only the second lowest silver plan in each group.
#      Note that Groupby preserves the sort order in each group, and nth(1) will remove 
#      groups without at least two elements.
silvers = plans[plans.metal_level == 'Silver'].drop(['plan_id', 'metal_level'], 1) \
            .sort_values(['state', 'rate_area', 'rate']) \
            .groupby(['state', 'rate_area']).nth(1)

# Find Second Lowest Cost Silver Plan (slcsp) for each zip code, by:
#   zip -> (state, rate_area), (state, rate_area) -> rate
slcsp_zip_col = slcsp['zipcode'].tolist()
slcsp_rate_col = slcsp['rate'].tolist()
for i in range(len(slcsp_zip_col)):
  zip = slcsp_zip_col[i]
  if(zip in zips_dict):
    rate_area = tuple(zips_dict[zip])
    if(rate_area in silvers.index):
      slcsp_rate_col[i] = silvers.ix[rate_area]['rate']    
slcsp['rate'] = slcsp_rate_col

# Write resulting slcsp data frame to csv file
slcsp.to_csv(SLCSP_FILE, index=False)