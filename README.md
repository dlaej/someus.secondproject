# someus - 교환일기 시스템 프로젝트
## Contents
+ Mybatis
+ AOP
+ Transaction
---------------------
## 1. Mybatis
**mybatis 등 모든 persistence framework는 내부적으로 jdbc를 이용하기 때문에 jdbc를 먼저 다룬다.**

> **jdbc란**

+ java database Connectivity로 java 어플리케이션과 데이터베이스를 연결시켜주는 api이다.

+ 자바를 이용한 db접속과 sql문장의 실행, 그리고 실행 결과로 얻어진 데이터의 핸들링을 제공하는 방법과 절차에 관한 규약.

+ 자바 프로그램 내에서 sql문을 실행하기 위한 자바 api.

> **jdbc 등장 배경**
+ 데이터베이스 접근의 표준화를 위해서

![jdbc](https://github.com/dlaej/someus.secondproject/blob/main/jdbc2.png)


[부연 설명](https://dlaej.notion.site/jdbc-78f2a3534116483ab0823ef5aeb2cb27?pvs=4)

> **jdbc는 3가지 기능을 표준 인터페이스로 정의하여 제공**
+ java.sql.Connection - 연결
+ java.sql.Statement - SQL을 담은 내용
+ java.sql.ResultSet - SQL 요청 응답

![jdbc표준인터페이스](https://github.com/dlaej/someus.secondproject/blob/main/jdbc%EC%9D%98%20%ED%91%9C%EC%A4%80%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A42.png)

+ 인터페이스인 jdbc api를 제공한다. 인터페이스가 이미 정의되어 있기 때문에, 어떤 db벤더든간에 똑같은 방법으로 사용하면 된다.
+ jdbc드라이버를 이용하여 데이터베이스와 연결한다. jdbc드라이버를 사용하기 위해서는 jdbc드라이버를 로딩해주는 작업이 필요하다.
+ jdbc드라이버: 데이터베이스와의 통신을 담당하는 인터페이스. Oracle, MS SQL, MYSQL 등과 같은 데이터베이스에 알맞은 jdbc드라이버를 구현하여 제공. jdbc 드라이버의 구현체를 이용해서 특정 벤더의 데이터베이스에 접근할 수 있음.

> **jdbc 동작 흐름**

cf)

+ 먼저 jdbc api를 사용하기 위해서는 jdbc드라이버를 먼저 로딩한 후 데이터베이스와 연결해야 한다.
+ jdbc드라이버는 jdbc 인터페이스를 구현한 구현체라고 생각할 수 있으며 특정 데이터베이스 벤더(Oracle, Mysql 등)에 대한 연결과 데이터베이스에 대한 작업을 가능하게 해준다.
+ jdbc 드라이버의 구현체를 이용해서 특정 벤더의 데이터베이스에 접근할 수 있음
+ jdbc가 제공하는 drivermanager가 드라이버들을 관리하고 connection을 획득하는 기능을 제공한다.
+ 이 획득한 connection을 통해서 데이터베이스에 sql을 실행하고 결과를 응답받을 수 있다.

> **jdbc API 사용 흐름 or jdbc 프로그래밍 방법 or jdbc 클래스의 생성관계**

![DriverManager](https://github.com/dlaej/someus.secondproject/blob/main/drivermanager.png)

1. 드라이버 로딩 시 DriverManager라는 객체가 갖고 있는 메서드를 이용해서 드라이버를 로딩한다.
2. 그래서 DriverManager 객체를 이용해서 connection 인스턴스를 얻어내고,
3. connection 인스턴스를 통해서 statement 객체를 얻어내고,
4. statement 객체를 통해 resultset을 얻어낸다.
5. 그래서 닫을 때는 열 때와 반대 순서로 닫아주어야 한다.

[부연 설명](https://dlaej.notion.site/jdbc-a4224e134697489e95f7b6ddce95093a?pvs=4)

+ 프레임워크는 반복되는 코드를 알아서 실행시켜준다.

> **커넥션 풀 등장 배경**

jdbc api를 사용하여 데이터베이스와 연결하기 위해 Connection 객체를 생성하는 작업은 비용이 많이 드는 작업 중 하나이다.

+ 커넥션 객체를 생성하는 과정
1. 애플리케이션에서 db드라이버를 통해 커넥션을 조회한다.
2. db드라이버는 db와 tctp/ip 커넥션을 연결한다.
3. db드라이버는 tcp/ip커넥션이 연결되면 아이디와 패스워드, 키타 부가 정보를 db에 전달한다.
4. db는 아이디, 패스워드를 통해 내부 인증을 거친후 내부에 db를 생성한다.
5. db는 커넥션 생성이 완료되었다는 응답을 보낸다.
6. db드라이버는 커넥션 객체를 생성해서 클라이언트에 반환한다.
   
-> 비효율적

> 이러한 문제를 해결하기 위해 애플리케이션 로딩 시점에 connection 객체를 미리 생성하고, 애플리케이션에서 데이터베이스에 연결이 필요할 경우 미리 준비된 connection 객체를 사용하여 애플리케이션의 성능을 향상하는 커넥션 풀이 등장하게 된다.

**connection 객체를 미리 생성하여 보관하고 애플리케이션이 필요할 때 꺼내서 사용할 수 있도록 관리해주는 것이 connection pool이다.**

1. 어플리케이션을 시작하는 시점에 커넥션 풀은 필요한 만큼 커넥션을 미리 생성하여 보관한다.
2. 서비스의 특징과 스펙에 따라 생성되는 connection 객체의 개수는 다르지만 일반적으로 기본값으로 10개를 생성한다.
3. 커넥션 풀에 들어있는 connection 객체는 tcp/ip로 db와 연결되어 있는 상태이기 때문에 즉시 sql을 db에 전달할 수 있다.
4. 즉, db드라이버를 통해 새로운 커넥션을 획득하는 것이 아닌 이미 생성되어 있는 커넥션을 참조하여 사용하게 된다.
5. 커넥션 풀에 있는 커넥션을 요청하면 커넥션 풀은 자신이 가지고 있는 커넥션 객체 중 하나를 반환한다.
6. 따라서 db드라이버를 통해 커넥션을 조회, 연결, 인증, sql을 실행하는 시간 등 커넥션 객체를 생성하기 위한 과정을 생략할 수 있게 된다.

> **datasource 등장 배경**

+ 커넥션을 획득할 때 driverManager를 통해서 커넥션을 획득하거나 커넥션풀을 통해서 커넥션을 획득하는 등 여러 방법이 존재한다. 그래서 datasource인터페이스를 통해서 커넥션을 획득하는 방법을 추상화한다.
+ 어떤 방식으로 커넥션을 획득하는지 상관없이 datasource 인터페이스를 통해 일관된 방식으로 데이터베이스와 통신할 수 있는 것이다.
+ 또한 인터페이스를 구현한 구현체를 쉽게 교체할 수 있어 어플리케이션의 유연성을 높일 수 있다.
+ 스프링과 스프링 부트에서는 datasource 인터페이스를 구현한 여러 구현체를 제공하는데 대표적으로 jdbc driverManager기반으로 한 driverManagerDataSource와 HikariCP 커넥션 풀을 기반으로 한 HikariDataSource가 있다. 

-> 그래서 만약에 db에 연결할 때마다 커넥션을 생성해서 획득하는 게 아닌 미리 커넥션을 생성해 놓은 커넥션 풀을 사용해서 커넥션을 획득하고 싶은 경우 datasource의 구현체 driverManagerDataSource를 hikariDataSource로 바꾸어 끼기만 하면 되는 것이다. 

-> 이로써 커넥션을 획득할 때 리소스를 효율적으로 활용할 수 있고 빠른 속도로 커넥션을 획득할 수 있다.

*hikaricp란 jdbc connection pool framework이다.*

모든 persistence framework는 내부적으로 jdbc api를 사용.

jdbc는 표준적인 api인터페이스만 제공할 뿐 실질적인 드라이버는 dbms제조사에서 제공한다.

jdbc와 mybatis 관계

> 영속성(persistence)이란?
+ 사라지지 않는 데이터의 특성.
+ 영속성을 갖는 데이터: db, 파일 등에 데이터를 영구적으로 저장하여 사라지지 않음.
+ 영속성을 갖지 않는 데이터: 메모리에만 데이터 존재, 프로그램 종료 시 데이터 사라짐.

> 소프트웨어 아키텍처
+ presentation Layer - business layer - persistence layer - database layer
+ presentation, business, persistence, database로 4계층으로 나뉘어져 있으며 각 계층마다 맡은 역할이 다름

![layer](https://github.com/dlaej/someus.secondproject/blob/main/layer.png)

-> 호출 방향: 위 순서

> presentation layer
+ 클라이언트의 요청을 받고 응답하는 계층.
+ 클라이언트의 요청에 어떻게 응답할지에 관심있는 계층.
+ 요청에 대한 처리는 business layer에 전달.
+ spring의 controller 부분.

> business layer
+ 비즈니스 로직 담당.
+ 클라이언트의 요청을 실제 처리하는 부분.
+ 클라이언트가 웹인지 앱인지, db가 어떤 종류인지 관심 없음.
+ 데이터 접근은 persistence layer에 위임.
+ spring의 service 부분

> persistence layer
+ db 접근 계층.
+ business 요청에 따라 db 저장, 조회, 삭제, 수정 등 로직 수행.
+ 데이터에 영속성을 부여해주는 계층.
+ jdbc를 이용하여 직접 구현할 수 있지만 persistence framework를 이용한 개발이 주.
+ spring의 repository 부분

> database layer
+ db 역할

> **persistence framework**
+ persistence layer에서 jdbc 프로그래밍의 복잡함 없이 간단하게 db 연동되는 시스템 개발 및 안정적인 구동을 보장하는 프레임워크이다.
+ 종류는 sql mapper, orm으로 두 가지이다.

> 1. 프로그램을 실행하는 동안 데이터들이 영구적으로 데이터베이스에 저장되도록 관련 작업을 해주어야 하는데 그 작업을 java언어에서는 jdbc가 지원해준다.
> 2. 그러나 번거로운 작업들이 수행되어야 하는데 이를 완화시켜준 것이 persistence framework이다.
> 3. persistence framwork는 sql mapper, orm 두 가지 기술이 있다.
> 4. 그 중 mybatis는 sql mapper이다.

> **sql mapper**
+ sql과 필드를 매핑
+ sql 문장으로 직접 db 다룸
+ mybatis, jdbcTemplates

> **orm**
+ 객체를 통해 db
+ db 데이터와 객체 매핑
+ 객체를 통해 db 데이터 다룸
+ 메서드 통해 간단한 sql 자동 생성 가능
+ jpa, hibernate

> **mybatis**
+ 객체와 sql문을 매핑
+ 내부에 jdbc 사용
+ sql로 직접 db 데이터 다룸
+ 복잡한 쿼리 짤 때 좋음
+ 간단한 crud 쿼리도 모두 작성해야 하는 단점 존재

> **jpa**
+ jpa는 인터페이스. 이를 사용하기 위해 구현체를 선택해야 한다
+ 객체와 db 데이터 매핑
+ jpa - 자바 표준 orm api, orm을 사용하기 위한 표준 인터페이스 모음
+ 객체를 통해 간접적으로 db 데이터 다룸
+ jpa 구현체 - hibernate, eclipseLink

> **mybatis의 원리**
+ 개발자가 jdbc api를 직접 호출하지 않고 mybatis에 일을 시키는 것이다.
+ 쿼리문은 사전에 정의된 mybatis mapper 파일에 정의되어있다.
+ 이렇게 mybatis를 사용하면 쿼리문이 mapper 파일에 정의되어 기존 자바 코드와 따로 분리되기 때문에 유지보수성 측면에서 더 좋고 추가 개발을 할 때에도 mapper 파일에만 추가하면 된다. 

> **mybatis 단점**
>> 엔티티에 컬럼이 하나 추가된다면 그 엔티티와 관련된 mapper 파일의 모든 crud 퀄이에 칼럼을 추가해줘야 한다.
>> 또한 mysql를 사용하다가 oracle로 데이터베이스를 옮겨야할 일이 생긴다면 모든 쿼리를 오라클 문법에 맞게 다 작성해야 할 것이다.
>> 이러한 현상은 기존의 jdbc 프로그래밍이 쿼리문에 의존하는 프로그래밍을 했기 때문이다.
>> 이렇듯 mybatis는 유지보수성과 코드 생산성 면에서 뛰어나지 않다.
>> 이러한 한계점을 해결한 것이 orm이자 jpa이다. 

> **mybatis란**
>> 객체 지향 언어인 자바의 관계형 데이터베이스 프로그래밍을 좀 더 쉽게 할 수 있게 도와주는 개발 프레임워크로서 jdbc를 통해 데이터베이스에 엑세스하는 작업을 캡슐화하고 일반 sql 쿼리, 저장 프로 시저 및 고급 매핑을 지원하며 모든 jdbc 코드 및 매개 변수의 중복작업을 제거 한다. mybatis에서는 프로그램에 있는 sql쿼리들을 한 구성파일에 구성하여 프로그램 코드와 sql을 분리할 수 있는 장점을 가지고 있다. 

![mybatis](https://github.com/dlaej/someus.secondproject/blob/main/mybatis3%20database%20access.jpg)

+ 기존 jdbc 프로그래밍의 경우 repository에서 곧바로 jdbc api 쪽으로 접근하여 db를 연결하였지만 위의 그림에 나와있듯이 mybatis를 사용할 경우 repository와 jdbc api 사이에 mybatis가 위치함으로써 편리한 access를 제공한다.


mybatis의 기능 구조

mybatis3의 database access 프로세스

![mybatis3](https://github.com/dlaej/someus.secondproject/blob/main/mybatis3.jpg)

1. 응용프로그램이 sqlsessionfactorybuilder를 위해 sqlsessionfactory를 빌드하도록 요청한다.
2. sqlsessionfactorybuilder는 sqlsessionfactory를 생성하기 위한 mybatis 구성 파일을 읽는다.
3. sqlsessionfactorybuilder는 mybatis 구성 파일의 정의에 따라 sqlsessionfactory를 생성한다.
4. 클라이언트가 응용 프로그램에 대한 프로세스를 요청한다.
5. 응용프로그램은 sqlsessionfactorybuilder를 사용하여 빌드된 sqlsessionfactory에서 sqlsession을 가져온다.
6. sqlsessionfactory는 sqlsession을 생성하고 이를 어플리케이션에 반환한다.
7. 응용프로그램이 sqlsession에서 매퍼 인터페이스의 구현 개체를 가져온다.
8. 응용프로그램이 매퍼 인터페이스 메서드를 호출한다
9. 매퍼 인터페이스의 구현 개체가 sqlsession 메서드를 호출하고 sql 실행을 요청한다.
10. sqlsession은 매핑 파일에서 실행할 sql을 가져와 sql을 실행한다

> mybatis-spring의 database access 구조

![mybatis-spring](https://github.com/dlaej/someus.secondproject/blob/main/mybatis-spring.png)

1. SqlsessionFactoryBean은 SqlsessionFactoryBuilder를 위해 SqlsessionFactory를 빌드하도록 요청한다.
2. 응용프로그램은 SqlsessionFactoryBuilder를 사용하여 빌드된 SqlsessionFactory에서 Sqlsession을 가져온다.
3. SqlsessionFactoryBuilder는 Mybatis 구성 파일의 정의에 따라 SqlsessionFactory를 생성한다. 따라서 생성된 SqlsessionFactory는 Spring DI 컨테이너에 의해 저장된다.
4. MapperFactoryBean은 안전한 Sqlsession(SqlsessionTemplate) 및 스레드 안전 매퍼 객체(Mapper 인터페이스의 프록시 객체)를 생성한다. 따라서 생성되는 매퍼 객체는 Spring DI컨테이너에 의해 저장되며 서비스 클래스 등에 DI가 적용된다. 매퍼 개체는 안전한 Sqlsesseion(SqlsessionTemplate)을 사용하여 스레드 안전 구현을 제공한다.
5. 클라이언트가 응용프로그램에 대한 프로세스를 요청한다.
6. 어플리케이션은 DI컨테이너에서 주입한 매퍼 개체(매퍼 인터페이스를 구현하는 프록시 개체)의 방법을 호출한다.
7. 매퍼 객체는 호출된 메소드에 해당하는 Sqlsession(SqlsessionTemplate) 메서드를 호출한다.
8. Sqlsession(SqlsessionTemplate)은 프록시 사용 및 안전한 Sqlsession 메서드를 호출한다.
9. 프록시 사용 및 스레드 안전 Sqlsession은 트랜잭션에 할당된 Mybatis3 표준 Sqlsession을 사용한다. 트랜잭션에 할당된 Sqlsession이 존재하지 않는 경우 SqlsessionFactory 메서드를 호출하여 표준 Mybatis3의 Sqlsession을 가져온다
10. SqlsessionFactory는 Mybatis3 표준 Sqlsession을 반환한다. 반환된 Mybatis3 표준 Sqlsession이 트랜잭션에 할당되기 때문에 동일한 트랜잭션 내에 있는 경우 새 Sqlsession을 생성하지 않고 동일한 Sqlsession을 사용한다. on 메서드를 호출하고 SQL 실행을 요청한다.
11. Mybatis3 표준 Sqlsession은 매핑 파일에서 실행할 SQL을 가져와 실행한다.

+ mybatis-spring은 개발자가 SqlsessionFactoryBean과 SqlsessionTemplate을 SpringBean 설정 파일에 bean으로 등록해야 한다. 참고로 SpringBean에는 db 연결 정보와 mybatis config 파일 정보, mapping 파일의 정보를 함께설정한다. 그리고 mybatis 설정 파일에는 vo 객체의 정보를 설정한다.
이 springbean 설정 파일은 datasouce정보와 mybatis config 파일 정보 mapping 파일의 정보를 읽고 sqlsessionfactorybean을 생성하고 이 sqlsessionfactorybean이 sqlsessionfactory를 생성하여 이 sqlsessionfactory가 sqlsession interface를 구현한 sqlsessiontemplate을 생성한다.

dbcp: 데이터베이스 커넥션 풀


## 2. AOP
> aop는 관점지향프로그래밍으로 관점을 기준으로 다양한 기능을 분리하여 보는 프로그래밍이다.
> 관점(Aspect)이란, 부가 기능과 그 적용처를 정의하고 합쳐서 모듈로 만든 것이다.

> **aop의 목적**
+ oop와 이름이 비슷하여 상반된 개념 같지만 관점지향프로그래밍은 객체지향 프로그래밍을 보완하기 위해 쓰인다.
+ 기존 객체지향프로그래밍은 목적에 따라 클래스를 만들고 객체를 만들었다. 따라서 핵심 비즈니스 로직이든 부가 기능의 로직이든 하나의 객체로 분리하는데 그치고 이 기능들을 어떻게 바라보고 나눠쓸지에 대한 정의가 부족하다는 단점이 있다.
+ 보통 비즈니스 웹 어플리케이션이라면 사업에 핵심적인 핵심 비즈니스 로직이 있고 어플리케이션 전체를 관통하는 부가 기능 로직이 있다.
+ 종류는 로깅, 보안, 트랜잭션이 있다. 부가 기능 로직의 코드를 핵심 비즈니스 로직의 코드와 분리하여 코드의 간결성을 높이고 변경에 유연함과 무한한 확장이 가능하도록 하는 것이 aop의 목적이다.

> **aop의 적용방식**

> 컴파일 시점 적용
+ 컴파일 시점 적용 방식은 aspectj 컴파일러가 일반 .java 파일을 컴파일할 때 부가기능을 넣어서 .class 파일로 컴파일해주는 것을 의미한다. 이 동작을 aspect와 실제 코드를 연결하는 위빙이라고 부른다.

> 클래스 로딩 시점 적용
+ jvm 내 클래스로더에 .class 파일을 올리는 시점에 바이트 코드를 조작해 부가기능 로직을 추가하는 방식이다.

> 런타임 시점 적용
+ 컴파일, 클래스 로딩, main()메서드의 실행 이후에 자바가 제공하는 범위 내에 부가 기능을 적용하는 방식이다. 이미 런타임 중이라 코드를 조작하기 어려워 스프링, 컨테이너, DI, 빈 등 여러 개념과 기능을 총동원하여 프록시를 통해 부가기능을 적용하는 방식이다.
+ 프록시는 메서드 실행 시점에서만 다음 타겟을 호출할 수 있기 때문에 런타임 시점에 부가기능을 적용하는 방식은 메서드의 실행 지점으로 제한된다.

> **spring aop**
+ spring aop는 런타임 시점에 적용하는 방식을 사용한다. 이유는 컴파일 시점과 클래스 로딩 시점에 적용하려면 별도의 컴파일러와 클래스로더 조작기를 써야 하는데 이것을 정하고 사용 및 유지하는 과정이 매우 어렵고 복잡하기 때문이다. 

> **aop 용어**
+ aspect
+ joinpint
+ advice
+ pointcut
+ target
+ advisor

## 3. Transaction
+ 트랜잭션은 db의 상태를 변경시키기 위해 수행하는 작업 단위이다.
+ db에서 하나의 거래를 안전하게 처리하도록 보장해주는 것이라고 할 수 있다.
+ 데이터베이스를 변경할 때의 커밋과 롤백을 시켜주는 최소의 단위를 말한다.

> **트랜잭션 기능**
+ db가 제공하는 트랜잭션 기능을 사용하면 commit과 rollback으로 정상적인 작업이 가능하도록 할 수 있다.
+ 작업 중 하나라도 실패를 해서 거래 이전으로 되돌리는 것을 롤백이라고 한다.
+ 그리고 모든 작업이 정상적으로 성공하는 경우 데이터베이스에 정상 반영하는 것을 커밋이라고 한다.

> 정리하자면 
+ 데이터 변경 쿼리를 실행하고 데이터베이스에 그 결과를 반영하려면 커밋 명령어인 commit을 호출하고 결과를 반영하고 싶지 안흐면 롤백 명령어인 rollback을 호출하면 된다.

> **spring이 제공하는 transaction 핵심 기술**

1. 트랜잭션 동기화
+ jdbc를 이용하는 개발자가 직접 여러 개의 작업을 하나의 트랜잭션으로 관리하려면 connection 객체를 공유하는 등 상당히 불필요한 작업들이 많이 생긴다.
+ spring은 이러한 문제를 해결하고자 트랜잭션 동기화 기술을 제공하고 있다.
+ 트랜잭션 동기화는 트랜잭션을 시작하기 위한 connection 객체를 특별한 저장소에 보관해두고 필요할 때 꺼내쓸 수 있도록 하는 기술이다.
+ 트랜잭션 동기화 저장소는 작업 쓰레드마다 connection 객체를 독립적으로 관리하기 때문에 멀티쓰레드 환경에서도 충돌이 발생할 여지가 없다.
+ 하지만 개발자가 jdbc가 아닌 hibernate와 같은 기술을 쓴다면 위의 jdbc 종속적인 트랜잭션 동기화 코드들은 문제를 유발하게 된다.
+ 대표적으로 hibernate에서는 connection이 아닌 session이라는 객체를 사용하기 때문이다.
+ 이러한 기술 종속적인 문제를 해결하기 위해 spring 트랜잭션 관리 부분을 추상화한 기술을 제공하고 있다. 

2. 트랜잭션 추상화
+ spring은 트랜잭션 기술의 공통점을 담은 트랜잭션 추상화 기술을 제공하고 있다.
+ 이를 이용함으로써 어플리케이션에 각 기술마다(jdbc, jpa, hibernate) 종속적인 코드를 이용하지 않고도 일관되게 트랜잭션을 처리할 수 있도록 해주고 있다.
+ spring이 제공하는 트랜잭션 경계 설정을 위한 추상 인터페이스는 platformTransactionManager이다.
+ 예를 들어 만약 jdbc의 로컬 트랜잭션을 이용한다면 datasourceTxManager를 이용하면 된다.
+ 이제 우리는 사용하는 기술과 무관하게 platformtransactionManager를 통해 다음의 코드와 같이 트랜잭션을 공유하고 커밋하고 롤백할 수 있게 되었다.
+ 하지만 위와 같은 트랜잭션 관리 코드들이 비즈니스 로직 코드와 결합되어 2가지 책임을 갖고 있다.
+ spring에서는 aop를 이용해 이러한 트랜잭션 부분을 핵심 비즈니스 로직과 분리하였다.

3. aop를 이용한 트랜잭션 분리
+ @Transactional 트랜잭션 어노테이션 사용
+ aop를 이용한 트랜잭션 분리는 어노테이션을 사용하고 트랜잭션 전파, 격리 수준, 제한 시간, 읽기 전용이라는 설정을 구현할 수 있다.







 
