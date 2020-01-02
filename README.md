# Google_Contacts_Cleansing

This is another piece of code which is a result of my excessive day sleeping leading to no sleep situation post midnight.
A very simple yet elegant project. I keep moving in different countries and there are thousands of contacts that are synced to my 
Google account. Some of these contacts are not in proper format i.e. some Indian contacts don't have +91, some start which 0 (which works 
if you are in the same country), some are automatically picking the wrong country code. Due to these, I have faced challenges adding contacts 
to WhatsApp or other communication apps. There are many more nuances present in those numbers. Doing this manually is just not possible, 
hence we go at 4 AM.

This is an effort to resolve all the issues in my Google contacts. There are several cases which I identified:

Case 1: all correct indian numbers; start with +91 and of length 13
Case 2: all correct US numbers; start with +1 and of length 12
Case 3: all correct indian numbers having +91 missing; length 10 and starts with 6-9
Case 4: all indian numbers start with 0; length 11 and starts with 0
Case 5: all random numbers; length less than 10 and don't have any '+'
Case 6: concatinated numbers, Google saves some number 
Case 7: all other numbers

The code does an analysis of how many contacts fall in each category and provides the required fixation whenever needed.
The project generates a CSV format file which can be directly uploaded (using import) to Google contacts after doing the desired changes.
