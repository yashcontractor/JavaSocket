<h1 align="center">Java Multicast Stock Values Application</h1>

This Java application showcases advanced socket programming by implementing a multicast communication system for broadcasting and receiving real-time stock values of fictional companies. The system is divided into two main components: a server that generates and sends stock updates, and a client that receives and displays these updates based on user preferences.

## Server Component

The server simulates real-time fluctuations in stock prices for four companies: Applet, Googlet, Teslat, and Metat. It employs Java's `MulticastSocket` and `DatagramPacket` classes to multicast stock values over a network. By applying random variations to predefined base values, the server generates dynamic stock prices and broadcasts this information to all subscribed clients without requiring direct connections to each.

## Client Component

Clients can subscribe to receive updates for all companies or select specific ones of interest. Upon receiving data, the client application filters and displays the latest stock values in real time. This process involves parsing multicast messages to extract and present relevant stock information efficiently. Additionally, the client incorporates a login mechanism to secure access to the stock data, ensuring that sensitive information remains protected.

## Key Features

- **Multicast Communication:** Efficiently broadcasts stock updates to multiple clients simultaneously.
- **Dynamic Stock Simulation:** Generates real-time fluctuations in stock prices for a realistic simulation.
- **Selective Monitoring:** Allows users to choose specific companies to monitor or to watch all available stocks.
- **Secure User Access:** Implements a login system for authenticating users and protecting data access.
