# Bot Character Framework
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fcorvis%2Fbot-character-framework.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fcorvis%2Fbot-character-framework?ref=badge_shield) [![Bintray](https://img.shields.io/bintray/v/corvis/maven/bot-character-framework.svg)](https://bintray.com/corvis/maven/bot-character-framework) [![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/corvis/bot-character-framework.svg)](https://github.com/corvis/bot-character-framework)

Bot Character Framework(BCF) is a java framework for building smart chat bots. It provides a bunch of abstractions and allows developer to concentrate on implementation of the business logic. Thus it provides:

* **Separate behavior logic into independent components** (called Skills)
* **Ability to create different sets of variations for bot replies**. You can randomly rotate answers for the same input, setup different reply styles to simulate different characters
* **Transport independent architecture**. You can connect your bot to literally any chat platform without need to modify cot logic. Transport layer is completely pluggable. 
* Testability and Isolation. Individual classes have distinct responsibilities like: skills, transports, nlu.

### When should I use Bot Character Framework?

Here are a couple of scenarios when this framework might be a good choice:

* You need to build chat bot in Java stack.
* You are limited in use of cloud based solutions (e.g. because of security policy). With BCF and RasaNLU  you can easily  create a great setup for on-premises deployment with 0 use of cloud services.
* You are interested in brining more humanity in your bot behavior.

## Documentation

To get started with Bot Character Framework please BCF's Documentation page. This describes key framework concepts, from what they are for, their structure and common use cases.

## Installation

In order to start using the framework add dependency on framework core library and optionally connect supplementary components like transports and NLU connectors.

**Maven**

````xml
<dependency>
  <groupId>org.bcf</groupId>
  <artifactId>bot-character-framework-core</artifactId>
  <version>1.0.4a</version>
  <type>pom</type>
</dependency>
````

**Gradle**

```groovy
compile 'org.bcf:bot-character-framework-core:1.0.4a'
```

## Copyright and License 

Copyright (c) 2017 Dmitry Berezovsky

This project is licensed under MIT license. Please check [LICENSE](LICENSE) for details. Please file an issue if you'd like alternative license like [Apache 2.0](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)).

[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fcorvis%2Fbot-character-framework.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fcorvis%2Fbot-character-framework?ref=badge_large)