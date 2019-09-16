# ledjer - A new kind of DLT

This project takes the concepts that are found in Quorum, Pantheon, Hyperledger Fabric and Corda around private transactions and abstracts them.

The core capability that each of these provides is the ability to send private transactions between parties.

Each implements a different mechanism of integrity.

ledjer provides a means to do this which is abstracted from any blockchain or DLT implementation.

This means that you can transact with peers and decide what mechanism of integrity you would like to use.

There are some key issues with existing implementations:

- No ability to choose between "on" or "off" ledger transactions
- Implementations strongly tied to 1 organisation == 1 node (no option for "multi tennancy")
- For Quorum no consensus on private SCs
- For fabric - static config
- For Quorum - immutable smart contract make evolution of logic heavyweight which leads to pushing logic out of smart contracts and reducing integrity
- Dynamic networks - Fabric and Corda have concepts around this but they are quite heavyweight. 

ledger intends to start with these constraints in mind.

There are two main design issues with current implementations:

- Complicated
- Complected (See "[Simple Made Easy](https://www.infoq.com/presentations/Simple-Made-Easy)" - Rich Hickey

ledjer will de-complect and make a DLT that is composable and simple. 

## Layers

- Foundation - P2P commms, sending transaction data, storing txData data, confidentiality, peer discovery, peer authentication
- Resilience and Scalability - horizontal scalability, redundancy
- Transaction Protocol - effectively 2-phase commit, a la corda [flows](https://docs.corda.net/key-concepts-flows.html)
- Non-repudiation (Digital signatures)
- Identity - Node identity, transaction signing identity, administrator identity, organisation identity
- Immutability - either via a blockchain recording hashes or ...?
- Integrity Verification (Notaries? Observers?)

## Temporal Scalability

One of the key problems with blockchains is that they contain all records, forever. So its always growing.

https://www.blockchain.com/charts/blocks-size?

Most accounting systems recognised that you can close of periods (end of Month, End of year). This is a scalabilty mechanism.

Opening balances and closing balances. These can be transactions themselves that create a state (have no input state).



## Protocol vs Implementation

The most important element if a technology it to become ubiquitous is that it is based on a protocol, not on an implementation. See HTTP.

Can a ledger protocol be developed that allows multiple implementations and configurable composition and ubiquity?

Can a ledger protocol conform to the architectural style of REST? Is it possible or desireable? The underlying style of REST is what contributes significantly to the scalability and adoption of HTTP and the web.







