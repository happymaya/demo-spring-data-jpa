# Spring Data ElasticSearch 在 Spring Data 中的用法


## Spring Data ElasticSearch

Spring Data 和 Elasticsearch 结合的时候，唯一需要注意的是**版本之间的兼容性问题**。

Elasticsearch 和 Spring Boot 是同时向前发展的，而 Elasticsearch 的大版本之间还存在一定的 API 兼容性问题，所以必须要知道这些版本之间的关系，如下:
| Spring Data Release Train | Spring Data Elasticsearch | Elasticsearch | Spring Boot |
| ------------------------- | ------------------------- | ------------- | ----------- |
| 2020.0.0[1]               | 4.1.x[1]                  | 7.9.3         | 2.4.x[1]    |
| Neumann                   | 4.0.x                     | 7.6.2         | 2.3.x       |
| Moore                     | 3.2.x                     | 6.8.12        | 2.2.x       |
| Lovelace                  | 3.1.x                     | 6.2.2         | 2.1.x       |
| Kay[2]                    | 3.0.x[2]                  | 5.5.0         | 2.0.x[2]    |
| Ingalls[2]                | 2.1.x[2]                  | 2.4.0         | 1.5.x[2]    |

**第一步：利用 Helm Chart 安装一个 Elasticsearch 集群 7.9.3 版本，执行命令如下：**