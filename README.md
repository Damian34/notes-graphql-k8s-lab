# Notes GraphQL & Kubernetes Lab

API for practicing GraphQL with CRUD and Kubernetes.  
(Kubernetes will be added later)

---

At the beginning, I'd like to share some of my thoughts.

### Hexagonal Architecture (Clean)

A clean Hexagonal Architecture is characterized by a business layer that:
- should not depend on any frameworks
- is sometimes also called "Ports and Adapters" architecture:
    - **port** – sometimes also called a contract. These are interfaces placed inside the domain. That means there's no implementation inside the domain, but it's known that they will provide or do something.
    - **adapter** – simply implementations of those ports, but located outside the domain. They often depend on frameworks and can be swapped.
- in my case, I have a package named `infrastructure`, but it doesn't have to be named that. Infrastructure elements can be split into multiple packages or even separate sub-projects or repositories.
- other concepts I won't elaborate on here include: policies, entities, aggregates, events, etc.

In my case, I have the Hexagonal Architecture contained within a single project.  
However, there's nothing stopping it from being split into multiple projects and composed properly in the `pom.xml` using dependencies.  
This can apply both to a modular project or one broken into separate repositories.

#### What are the advantages of this approach?

If we have a well-isolated business layer and not just a superficial one where the domain only contains models,  
then we can replace infrastructure and adapter implementations freely.  
For example, imagine we have an application running on Spring Boot and want to migrate to Quarkus for better performance. The core or domain part can stay the same.

#### What are the disadvantages?

The most noticeable issue is mapping. Take Hibernate entities as an example.

Let’s walk through the full flow of a method handling an HTTP GET request:  
We receive a request, then either map it or pass it directly to the domain and adapter.  
The adapter fetches an entity. Hibernate first loads a map and then maps it to a JPA entity. So we already have two mappings.  
However we can't return this directly to the domain, so we map it a third time into a domain object.  
That could already be used in the response, but sometimes we map it once more into a response object if we want to add logic.  
This means we may have 3 or 4 mappings, which can be slow when dealing with large volumes of data.

However, if we shift the logic more toward an "Anemic Domain Model" and drop Hibernate in favor of manual construction,  
we can reduce this to a single mapping. That could improve performance.  
For example, by replacing JPA and Hibernate with JOOQ or JdbcTemplate.

---

### Hexagonal Architecture (Dirty)

In this project where I separated the business layer,  
it’s clear that this CRUD api could have been written more simply without splitting the domain at all.

For example, we could place JPA entities and repositories directly in the domain and work with them there.  
The drawback is that we no longer have a clean architecture. Instead, we have something closer to an Anemic Domain Model.  
Sometimes I even refer to it as "dirty" because we intentionally simplify the model at the expense of separation of concerns.

---

### Why am I bringing this up?

I remember situations in microservices where Domain-Driven Design (DDD) was used,  
and sometimes the goal was to fully isolate the business layer. That’s exactly what I did here with the `domain` package.  
In hexagonal terms, the `domain` is sometimes called the `core`.

However, based on my observations, it is more common to make some architectural compromises.  
This means designing the code in a way that keeps structure consistent across the entire project.  
I also try to aim for that.  
The most common example is allowing the model to be stored together with JPA entities inside the domain,  
or referencing them directly even though they live outside the domain and lack a formal contract or interface.

Of course, I’m touching here on DDD, microservices, and subdomains.  
Recently I came across an approach where a subdomain exposes only a public facade.  
To me, this makes the subdomain feel like a mini-monolith.  
This approach doesn’t seem to be very popular, but after some thought, it looks quite suitable for applying TDD.

---

Link to video on Hexagonal Architecture and Packed Scopes:  
📺 https://youtu.be/ILBX9fa9aJo
