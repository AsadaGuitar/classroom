# classroom
<img src="https://img.shields.io/badge/build-passed-red" /> <img src="https://img.shields.io/badge/scala-2.13.8-blue?logo=scala"/> <img src="https://img.shields.io/badge/docker-20.10.12-blue?logo=docker"/>


## 技術スタック
+ Scala 2.13.8
+ REST API Application
+ DDD Clean Architecture
+ Akka (-Typed, -Http)
+ Slick3


## レイヤードストラクチャ

### Domain
+ [Classroom](https://github.com/AsadaGuitar/classroom/blob/master/src/main/scala/com/github/AsadaGuitar/classroom/domain/Classroom.scala)
  + 教室のドメインロジックを定義
  
### Repository
+ [ClassroomsRepository](https://github.com/AsadaGuitar/classroom/blob/master/src/main/scala/com/github/AsadaGuitar/classroom/domain/repository/ClassroomsRepository.scala)
  + 教室オブジェクトの永続化を行うトレイト
+ [StudentsRepository](https://github.com/AsadaGuitar/classroom/blob/master/src/main/scala/com/github/AsadaGuitar/classroom/domain/repository/StudentsRepository.scala)
  + 生徒オブジェクトの永続化を行うトレイト

### UseCase
+ [ClassroomsUseCase](https://github.com/AsadaGuitar/classroom/blob/master/src/main/scala/com/github/AsadaGuitar/classroom/usecase/ClassroomsUseCase.scala)
  + 教室のアプリケーションロジックを定義

### Interface-Adaptors
+ [ClassroomsRepositoryImpl](https://github.com/AsadaGuitar/classroom/blob/master/src/main/scala/com/github/AsadaGuitar/classroom/adaptor/postgres/ClassroomsRepositoryImpl.scala)
  + ClassroomsRepositoryの具象クラス
+ [StudentsRepositoryImpl](https://github.com/AsadaGuitar/classroom/blob/master/src/main/scala/com/github/AsadaGuitar/classroom/adaptor/postgres/StudentsRepositoryImpl.scala)
  + StudentsRepositoryの具象クラス
+ [ClassroomsController](https://github.com/AsadaGuitar/classroom/blob/master/src/main/scala/com/github/AsadaGuitar/classroom/controller/ClassroomsController.scala)
  + APIを定義


## ER図
![classroom_er drawio](https://user-images.githubusercontent.com/79627592/197338250-bce2b0a8-4830-4427-bee5-62b2f9054048.png)



## アプリケーションの起動方法

```shell
$ git clone https://github.com/AsadaGuitar/classroom.git
$ cd classroom
$ docker-compose up -d       
$ docker-compose run --service-ports sbt /bin/bash 
# sbt shell
$ sbt run
```

## 提供する機能

### Path
+ GET /students

### Parameters
先頭に (*) がある項目は必須
+ *facilitator_id (int): 先生のIDを指定します
+ page (int): 表示するページ番号を指定します
+ limit (int): ページに表示するデータ数を指定します
+ sort (string): ソートキーを指定します(名前 : name | ログインID: loginId)
+ order (string): 昇順・降順を指定します(昇順 : asc | 降順: desc)
+ {key}_like (string): 指定した属性による部分一致検索をします(名前 : name_like | ログインID: loginId_like)

### 教師が担当している生徒の一覧を取得
```shell
$ curl -X GET 'http://localhost:9000/students?facilitator_id=1'
{
  "students": [
    {
      "classroom": {
        "id": 2,
        "name": "高校特進クラス"
      },
      "id": 50,
      "loginId": "shimada",
      "name": "島田奏太"
    },
    {
      "classroom": {
        "id": 2,
        "name": "高校特進クラス"
      },
      "id": 44,
      "loginId": "takeuchi",
      "name": "竹内悠人"
    },
    ~
  ],
  "totalCount": 18
}
```

### 教師が担当している生徒の一覧をページングを行い取得
```shell
# ３件/１ページの２ページ目を取得する。
$ curl -X GET 'http://localhost:9000/students?facilitator_id=3&page=2&limit=3'
{
  "students": [
    {
      "classroom": {
        "id": 4,
        "name": "中学特進クラス"
      },
      "id": 40,
      "loginId": "nakano",
      "name": "中野匠"
    },
    {
      "classroom": {
        "id": 4,
        "name": "中学特進クラス"
      },
      "id": 34,
      "loginId": "hujita",
      "name": "福田葵"
    },
    {
      "classroom": {
        "id": 4,
        "name": "中学特進クラス"
      },
      "id": 28,
      "loginId": "saito",
      "name": "斉藤ひなた"
    }
  ],
  "totalCount": 3
}
```

### 教師が担当している生徒の一覧をソートして取得
```shell
# 生徒IDを昇順ソートで取得する。
$ curl -X GET 'http://localhost:9000/students?facilitator_id=1&sort=id&order=asc&page=2&limit=3'
{
  "students": [
    {
      "classroom": {
        "id": 1,
        "name": "高校普通クラス"
      },
      "id": 7,
      "loginId": "inoue",
      "name": "井上咲良"
    },
    {
      "classroom": {
        "id": 2,
        "name": "高校特進クラス"
      },
      "id": 8,
      "loginId": "kimura",
      "name": "木村陽葵"
    },
    {
      "classroom": {
        "id": 1,
        "name": "高校普通クラス"
      },
      "id": 13,
      "loginId": "abe",
      "name": "阿部陽葵"
    }
  ],
  "totalCount": 3
}
```

### 教師が担当している生徒の一覧をライク検索で取得
```shell
# ライク検索「田」で取得する。
$ curl 'http://localhost:9000/students' \
> --get \
> --data-urlencode 'facilitator_id=4' \
> --data-urlencode 'page=1' \
> --data-urlencode 'limit=3' \                                                                  
> --data-urlencode 'sort=id' \
> --data-urlencode 'order=asc' \
> --data-urlencode 'name_like=田' 
{
  "students": [
    {
      "classroom": {
        "id": 1,
        "name": "高校普通クラス"
      },
      "id": 19,
      "loginId": "maeda",
      "name": "前田芽依"
    },
    {
      "classroom": {
        "id": 5,
        "name": "小学普通クラス"
      },
      "id": 23,
      "loginId": "okada",
      "name": "岡田莉子"
    },
    {
      "classroom": {
        "id": 5,
        "name": "小学普通クラス"
      },
      "id": 35,
      "loginId": "ota",
      "name": "太田海斗"
    }
  ],
  "totalCount": 3
}
```
