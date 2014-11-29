demos [![Build Status](https://travis-ci.org/han-feng/demos.svg?branch=master)](https://travis-ci.org/han-feng/demos) [![Coverage Status](https://coveralls.io/repos/han-feng/demos/badge.png?branch=master)](https://coveralls.io/r/han-feng/demos?branch=master)
=====

学习开源技术过程中积累的Demo集合

子项目名称 | 运行方式 | 说明 |
-------------|-----------|------|
demos-jsr223 | mvn test | JSR-223(Scripting for the Java Platform)演示， <br> 包含解释执行和预编译两种案例， <br> 涵盖 JavaScript（JRE内置）、Groovy、Clojure、Python、Ruby五种脚本。|
demos-drools | mvn test | Dools 6 的简单案例，特别包含 CEP 场景中典型的滑动时间窗的使用演示。 |
demos-mybatis | mvn test | mybatis的简单案例 |
demos-druid | mvn tomcat7:run <br> 然后用浏览器访问 http://localhost:9999/demos-druid | 演示了 Druid 数据库连接池提供的监控能力 |
