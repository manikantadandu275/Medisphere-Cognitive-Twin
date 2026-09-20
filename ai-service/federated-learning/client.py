print("MediSphere Federated Learning")
print("-----------------------------")

client1_data = [78, 80, 76]
client2_data = [82, 79, 81]

client1_average = sum(client1_data) / len(client1_data)
client2_average = sum(client2_data) / len(client2_data)

global_average = (client1_average + client2_average) / 2

print("Client 1 average:", client1_average)
print("Client 2 average:", client2_average)
print("Global average:", global_average)