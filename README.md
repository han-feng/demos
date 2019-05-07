# demos

 [![Build Status](https://travis-ci.org/han-feng/demos.svg?branch=master)](https://travis-ci.org/han-feng/demos)
 [![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=demos%3Ademos&metric=alert_status)](https://sonarcloud.io/dashboard?id=demos%3Ademos)
 [![Codacy Badge](https://api.codacy.com/project/badge/Grade/5c7c48133bec4c6295be6ae8d67505d5)](https://www.codacy.com/app/han-feng/demos?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=han-feng/demos&amp;utm_campaign=Badge_Grade)
 [![Coverage Status](https://coveralls.io/repos/github/han-feng/demos/badge.svg?branch=master)](https://coveralls.io/github/han-feng/demos?branch=master)
 [![codecov](https://codecov.io/gh/han-feng/demos/branch/master/graph/badge.svg)](https://codecov.io/gh/han-feng/demos)

学习开源技术过程中积累的 Demo 集合

| 子项目名称    | 运行方式                                                                | 说明                                                                                                                                                                     |
| ------------- | ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| demos-jsr223  | mvn test                                                                | JSR-223 (Scripting for the Java Platform) 演示， <br> 包含解释执行和预编译两种案例， <br> 涵盖 JavaScript（JRE 内置）、Clojure、Lua、Groovy、Python、Ruby 六种脚本语言。 |
| demos-graphql | mvn test                                                                | GraphQL 简单案例。                                                                                                                                                       |
| demos-drools  | mvn test                                                                | Drools 6 的简单案例，特别包含 CEP 场景中典型的滑动时间窗的使用演示。                                                                                                     |
| demos-mybatis | mvn test                                                                | mybatis 的简单案例                                                                                                                                                       |
| demos-druid   | mvn tomcat7:run <br> 然后用浏览器访问 http://localhost:9990/demos-druid | 演示了 Druid 数据库连接池提供的监控能力                                                                                                                                  |
