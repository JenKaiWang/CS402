import matplotlib.pyplot as plt
import numpy as np

# Read the trace file
with open('C:\\Users\\User\\Desktop\\CS402\\HW01\\spice.din', 'r') as file:
    data = file.readlines()

# Extract addresses
addresses = [int(line.split()[1], 16) for line in data]

# Create histogram
plt.hist(addresses, bins=20, color='blue', alpha=0.7)
plt.xlabel('Address (Decimal)')
plt.ylabel('Number of Occurrences')
plt.title('Address Distribution Histogram for spice.din')
plt.grid(True)
plt.xticks(np.linspace(min(addresses), max(addresses), 10))
plt.show()
