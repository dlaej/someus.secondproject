# mybatis 
mybatis는 내부적으로 jdbc를 이용한다.
jdbc는 자바 응용프로그램과 데이터베이스를 연결해주는 자바 api이다.
프로그램을 실행하는 동안 데이터들이 영구적으로 데이터베이스에 저장되도록 관련 작업을 해주어야 하는데 그 작업을 java언어에서는 jdbc가 지원해준다. 그러나 번거로운 작업들이 수행되어야 하는데 이를
mybatis는 sql mapper.
jdbc란? java database Connectivity로 java 어플리케이션과 데이터베이스를 연결시켜주는 api이다. 자바를 이용한 db접속과 sql문장의 실행, 그리고 실행 결과로 얻어진 데이터의 핸들링을 제공하는 방법과 절차에 관한 규약. 자바 프로그램 내에서 sql문을 실행하기 위한 자바 api.
jdbc 등장 배경
데이터베이스 접근의 표준화를 위해서
데이터베이스에는 oracle database, mysql과 같이 여러 종류의 데이터베이스가 있다.
각각 데이터베이스마다 sql를 전달하거나 결과를 응답받는 방법들이 다 다르고 데이터베이스의 종류는 수십 개가 존재한다.
jdbc가 존재하기 전에는 이런 데이터베이스마다 존재하는 고유한 api를 직접 사용했었다.
이에 따라 개발자는 기존의 데이터베이스를 다른 데이터베이스로 교체해야 하는 경우에는 데이터베이스에 맞게 기존의 코드를 모두 수정해야 했으며 심지어 각각의 데이터베이스를 사용하는 방법도 새로 학습해야 했다.
따라서 표준이라는 게 필요했으며 jdbc의 표준 인터페이스 덕분에 개발자는 데이터베이스를 쉽게 변경할 수 있게 되었고 변경에 유연하게 대처할 수 있게 되었다.
사실 jdbc는 오래된 기술이며 사용하는 방법도 많이 복잡하다. 그래서 jdbc를 직접 사용하기보다는 데이터베이스 접근을 더 편리하게 하고 개발 생산성을 높이기 위한 기술인 sql mapper와 orm을 주로 사용하게 된다. 그런데도 jdbc를 알아야 하는 이유는 사실 sql mapper나 orm은 jdbc를 기반으로 동작한다. 이들의 기술은 jdbc의 기능고 ㅏ개념을 내부적으로 활용하여 데이터베이스와의 상호작용을 처리한다.
따라서 jdbc를 이해하면 sql mapper나 orm이 어떻게 동작하는지 더 잘 이해할 수 있는 베이스가 되며 또한 문제가 발생했을 때 근본적인 문제를 해결할 수 있다. 
jdbc는 3가지 기능을 표준 인터페이스로 정의하여 제공한다.
java.sql.Connection - 연결
java.sql.Statement - SQL을 담은 내용
java.sql.ResultSet - SQL 요청 응답
jdbc드라이버를 이용하여 데이터베이스와 연결한다. jdbc드라이버를 사용하기 위해서는 jdbc드라이버를 로딩해주는 작업이 필요하다.
인터페이스인 jdbc api를 제공한다. 인터페이스가 이미 정의되어 있기 때문에, 어떤 db벤터든간에 똑같은 방법으로 사용하면 된다.
jdbc드라이버: 데이터베이스와의 통신을 담당하는 인터페이스. Oracle, MS SQL, MYSQL 등과 같은 데이터베이스에 알맞은 jdbc드라이버를 구현하여 제공. jdbc 드라이버의 구현체를 이용해서 특정 벤터의 데이터베이스에 접근할 수 있음.
jdbc 동작 흐름 
먼저 jdbc api를 사용하기 위해서는 jdbc드라이버를 먼저 로딩한 후 데이터베이스와 연결해야 한다.
jdbc드라이버는 jdbc 인터페이스를 구현한 구현체라고 생각할 수 있으며 특정 데이터베이스 벤더(Oracle, Mysql 등)에 대한 연결과 데이터베이스에 대한 작업을 가능하게 해준다. jdbc 드라이버의 구현체를 이용해서 특정 벤더의 데이터베이스에 접근할 수 있음
jdbc가 제공하는 drivermanager가 들아ㅣ버들을 관리하고 connection을 획득하는 기능을 제공한다.
이 획득한 connection을 통해서 데이터베이스에 sql을 실행하고 결과를 응답받을 수 있다.
jdbc API 사용 흐름. jdbc를 이용한 프로그래밍 방법. jdbc 클래스의 생성관계
드라이버 로딩 시 DriverManager라는 객체가 갖고 있는 메서드를 이용해서 드라이버를 로딩한다. 그래서 DriverManager 객체를 이용해서 connection 인스턴스를 얻어내고, connection 인스턴스를 통해서 statement 객체를 얻어내고, statement 객체를 통해 resultset을 얻어낸다. 그래서 닫을 때는 열 때와 반대 순서로 닫아주어야 한다.
jdbc드라이버 로딩: 사용하고자 하는 jdbc드라이버를 로딩한다. jdbc드라이버는 DriverManager클래스를 통해 로딩된다. 이 과정이 아까 특정 벤더의 데이터베이스를 선택하는 과정인 것 같다.
Connection 객체 생성: jdbc드라이버가 정상적으로 로딩되면 DriverManager를 통해 데이터베이스와 연결되는 session인 Connection 객체를 생성한다.
Statement 객체 생성: Statement 객체는 작성된 SQL 쿼리문을 실행하기 위한 객체로 정적 SQL쿼리 문자열을 입력으로 가진다. ** session이란?
Query 실행: 생성된 Statement 객체를 이용하여 입력한 SQL 쿼리를 실행한다.
ResultSet 객체로부터 데이터 조회: 실행된 SQL 쿼리문에 대한 결과 데이터 셋이다.
ResultSet 객체 Close: jdbc api를 통해 사용된 객체들은 생성된 객체들을 사용한 순서의 역순으로 Close한다.
Statement 객체 Close
Connection 객체 Close
순서대로 환경구성. import. 드라이버 로드. connection 얻기. statement 생성 및 질의수행. resultset으로 결과 받기. close.
의존성 추가 (jdbc를 사용하겠다는 것은 db를 사용하겠다는 것임)
자바코드에서 나랑 다른 패키지에 있는 클래스를 사용하기 위해서 반드시 import해줘야 한다.
import java.sql.*;
드라이버 로드 
class.forName("com.mysql.jdbc.Driver");
이는 드라이버를 로드하는 코드인데 각각 db벤더에서 제공하는 객체이다. jdbc.Driver는 패키지명.클래스명이다. 해당 객체를 class라는 클래스가 갖고 있는 forName이라는 메소드를 이용하면 해당 객체가 메모리에 올라간다.
connection 얻기
String dburl = "jdbc:mysql://localhost/dbName";
Connection con = DriverManager.getConnection(dburl,ID,PWD);
connection 객체를 얻어낼 때 사용하는 객체가 driverManager라는 객체이다. 그래서 driverManager가 갖고 있는 getConnection을 이용하면 어떤 url에 접속할 거냐(DB가 있는 ip주소), 즉 db가 있는 url이다. 그리고 db의 user, password를 입력해주면 연결할 수 있다. 이때 dburl을 정하는 방식도 db에 따라 다른데, 가각 db 벤더에서 알려준다.
connection 객체 생성 = db에 접속하겠다. connection 객체는 db가 접속됐을 때 얻어내줄 수 있는 객체이다. connection 객체라곤 하지만 connection은 인터페이스이고, 이 객체는 각각의 벤더가 구현하고 있는 객체여야 한다. 그러려면 벤더가 제공해주는 라이브러리를 사용할 수 있어야 하는데 이것을 사용할 수 있게 해주는 것이 로딩하는 것이다. 즉 드라이버를 로드하는 것이다. 그래서 로드먼저 해야 한다.
public static Connection getConnection() throws Exception{
	//오라클 사용시 정의되는 URL 형식
	String url = "jdbc:oracle:thin:@117.16.46.111:1521:xe"; 
	String user = "smu"; //DB의 user
	String password = "smu";//DB의 password
	Connection conn = null;
    //오라클 벤더가 제공하고 있는 클래스 이름
	Class.forName("oracle.jdbc.driver.OracleDriver"); 
	conn = DriverManager.getConnection(url, user, password);
	return conn;
}
statement 생성 및 질의수행 
statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("select no from user");
명령 생성으로 실제 쿼리를 사용하면 된다. 이런 쿼리를 사용하기 위해서는 반드시 statement 객체를 얻어야 한다. 
statement 객체에 쿼리문을 작성하고 실행해달라는 메서드(execute)를 사용한다. 해당 메서드는 실행할 쿼리가 select냐 insert냐 update냐 delete냐에 따라서 메서드가 달라지낟. 
그리고 statement 객체를 이용해서 resultSet을 얻어낼 수 있다.
statement객체가 하는 일은 select...하고 만드는 것이다. 즉, 쿼리문을 생성하고 수행하도록 도와주는 것이다. 이 statement도 인터페이스이고, 실제로 db벤더가 무엇이냐에 따라서 이 statement객체를 구현한 객체가 리턴이 될 것이다.
resultset으로 결과 받기
ResultSet rs = stmt.executeQuery("select no from user");
whie(rs.next())
System.out.pritnln(rs.getInt("no"));
현제 ResultSet은 db쪽에 있고, 이 db resultset에 레퍼런스(rs)를 얻어온 것이고, 이를 가지고 실행해야 한다.
클라이언트가 우리 프로그램이라고 했을 때, 실행해달라 했을 때 결과를 준다 했는데 이 결과셋은 db가 가지고 있는 것이고 이 결과값을 가리킬 수 있는 레퍼런스 변수만 온다.
즉, resultset에서 시작해서 하나씩 꺼내온다.
그런 다음에 데이터베이스가 준 결과값을 가지고 결과를 꺼내올 수 있는데 만야 sql문에 결과물이 있으면 resultset객체를 생성한다. 그래서 쿼리문이 뭐냐에 따라 조금 다르게 실행된다.
close
rs.close();
stmt.close();
con.close();
그리고 모든 객체를 닫아야 한다. 단 닫는 순서가 반드시 거꾸로여야 한다. 
가장 마지막에 열린 resultset(rs)를 가장 먼저 닫아줘야 한다.

프레임어크는 반복되는 코드를 알아서 실행시켜준다.
커넥션 풀: jdbc api를 사용하여 데이터베이스와  연결하기 위해 Connection 객체를 생성하는 작업은 비용이 많이 드는 작업 중 하나이다.
커넥션 객체를 생성하는 과정
애플리케이션에서 db드라이버를 통해 커넥션을 조회한다.
db드라이버는 db와 tctp/ip 커넥션을 연결한다.
db드라이버는 tcp/ip커넥션이 연결되면 아이디와 패스워드, 키타 부가 정보를 db에 전달한다.
db는 아이디, 패스워드를 통해 내부 인증을 거친후 내부에 db를 생성한다.
db는 커넥션 생성이 완료되었다는 응답을 보낸다.
db드라이버는 커넥션 객체를 생성해서 클라이언트에 반환한다.
-> 비효율적
이러한 문제를 해결하기 위해 애플리케이션 로딩 시점에 connection 객체를 미리 생성하고, 애플리케이션에서 데이터베이스에 연결이 필요할 경우 미리 준비된 connection 객체를 사용하여 애플리케이션의 성능을 향상하는 커넥션 풀이 등장하게 된다.
connection 객체를 미리 생성하여 보관하고 애플리케이션이 필요할 때 꺼내서 사용할 수 있도록 관리해주는 것이 connection pool이다. 
애플리케이션을 시작하는 시점에 커넥션 풀은 필요한 만큼 커넥션을 미리 생성하여 보관한다.
서비스의 특징과 스펙에 따라 생성되는 connection 객체의 개수는 다르지만 일반적으로 기본값으로 10개를 생성한다.
커넥션 풀에 들어있는 connection 객체는 tcp/ip로 db와 연결되어 있는 상태이기 때문에 즉시 sql을 db에 전달할 수 있다.
즉, db드라이버를 통해 새로운 커넥션을 획득하는 것이 아닌 이미 생성되어 있는 커넥션을 참조하여 사용하게 된다.
커넥션 풀에 있는 커넥션을 요청하면 커넥션 풀은 자신이 가지고 있는 커넥션 객체 중 하나를 반환한다.
따라서 db드라이버를 통해 커넥션을 조회, 연결, 인증, sql을 실행하는 시간 등 커넥션 객체를 생성하기 위한 과정을 생략할 수 있게 된다.

커넥션을 획득할 때 driverManager를 통해서 커넥션을 획득하거나 커넥션풀을 통해서 커넥션을 획득하는 등 여러 방법이 존재한다. 그래서 datasource인터페이스를 통해서 커넥션을 획득하는 방법을 추상화한다. 
어떤 방식으로 커넥션을 획득하는지 상관없이 datasource 인터페이스를 통해 일관된 방식으로 데이터베이스와 통신할 수 있는 것이다.
또한 인터페이스를 구현한 구현체를 쉽게 교체할 수 있어 어플리케이션의 유연성을 높일 수 있다.
스프링과 스프링 부트에서는 datasource 인터페이스를 구현한 여러 구현체를 제공하는데 대표적으로 jdbc driverManager기반으로 한 driverManagerDataSource와 HikariCP 커넥션 풀을 기반으로 한 HikariDataSource가 있다. 
그래서 만약에 db에 연결할 때마다 커넥션을 생성해서 획득하는 게 아닌 미리 커넥션ㅇ르 생성해 놓은 커넥션 풀을 사용해서 커넥션을 획득하고 싶은 경우 datasource의 구현체 driverManagerDataSource를 hikariDataSource로 바꾸어 끼기만 하면 되는 것이다. 이로써 커넥션을 획득할 때 리소스를 효율적으로 활용할 수 있고 빠른 속도로 커넥션을 획득할 수 있다.
hikaricp란 jdbc connection pool framework이다.
모든 persistence framework는 내부적으로 jdbc api를 사용.
jdbc는 표준적인 api인터페이스만 제공할 뿐 실질적인 드라이버는 dbms제조사에서 제공한다.
jdbc와 mybatis 관계
영속성(persistence)이란?
사라지지 않는 데이터의 특성
영속성을 갖는 데이터: db, 파일 등에 데이터를 영구적을 ㅗ저장하여 사라지지 않음
영속성을 갖지 않는 데이터: 메모리에만 데이터 존재, 프로그램 종료 시 데이터 사라짐.
소프트웨어 아키텍처
presentation Layer - business layer - persistence layer - database layer
presentation, business, persistence, database로 4계층으로 나뉘어져 있으며 각 계층마다 맡은 역할이 다름
호출 방향: 위 순서
presentation layer
클라이언트의 요청을 받고 응답하는 계층
클라이언트의 요청에 어떻게 응답할지에 관심있는 계층
요청에 대한 처리는 business layer에 전달
spring의 controller 부분
business layer
비즈니스 로직 담당
클라이언트의 요청을 실제 처리하는 부분
클라이언트가 웹인지 앱인지, db가 어떤 종류인지 관심 없음
데이터 접근은 persistence layer에 위임
spring의 service 부분
persistence layer
db 접근 계층
business 요청에 따라 db 저장, 조회, 삭제, 수정 등 로직 수행
데이터에 영속성을 부여해주는 계층
jdbc를 이용하여 직접 구현할 수 있지만 persistence framework를 이용한 개발이 주
spring의 repository 부분
database layer
db 역할
persistence framework
persistence layer에서 jdbc 프로그래밍의 복잡함 없이 간단하게 db 연동되는 시스템 개발 및 안정적인 구동 보장하는 프레임워크이다. 종류는 sql mapper, orm으로 두 가지이다.
sql mapper
sql과 필드를 매핑
sql 문장으로 직접 db 다룸
mybatis, jdbcTemplates
orm
객체를 통해 db
db 데이터와 객체 매핑
객체를 통해 db 데이터 다룸
메서드 통해 간단한 sql 자동 생성 가능
jpa, hibernate
mybatis
객체와 sql문을 매핑
내부에 jdbc 사용
sql로 직접 db 데이터 다룸
복잡한 쿼리 짤 때 좋음
간단한 crud 쿼리도 모두 작성해야 하는 단점 존재
jpa
jpa는 인터페이스. 이를 사용하기 위해 구현체를 선택해야 한다
객체와 db 데이터 매핑
jpa - 자바 표준 orm api, orm을 사용하기 위한 표준 인터페이스 모음
객체를 통해 간접적으로 db 데이터 다룸
jpa 구현체 - hibernate, eclipseLink
mybatis의 원리는 간단하다.
개발자가 jdbc api를 직접 호출하지 않고 mybatis에 일을 시키는 것이다. 쿼리문은 사전에 정의된 mybatis mapper 파일에 정의되어있다. 
이렇게 mybatis를 사용하면 쿼리문이 mapper 파일에 정의되어 기존 자바 코드와 따로 분리되기 때문에 유지보수성 측면에서 더 좋고 추가 개발을 할 ㄹ때에도 mapper 파일에만 추가하면 된다. 
mybatis 단점
엔티티에 컬럼이 하나 추가된다면 그 엔티티와 관련된 mapper 파일의 모든 crud 퀄이에 칼럼을 추가해줘야 한다. 또한 mysql를 사용하다가 oracle로 데이터베이스를 옮겨야할 일이 생긴다면 모든 쿼리를 오라클 문법에 맞게 다 작성해야 할 것이다. 이러한 현상은 기존의 jdbc 프로그래밍이 쿼리문에 의존하는 프로그래밍을 했기 때문이다. 이렇듯 mybatis는 유지보수성과 코드 생산성 면에서 뛰어나지 않다. 이러한 한계점을 해결한 것이 orm이자 jpa이다. 
mybatis란
객체 지향 언어인 자바의 관계형 데이터베이스 프로그래밍을 좀 더 쉽게 할 수 있게 도와주는 개발 프레임워크로서 jdbc를 통해 데이터베이스에 엑세스하는 작업을 캡슐화하고 일반 sql 쿼리, 저장 프로 시저 및 고급 매핑을 지원하며 모든 jdbc 코드 및 매개 변수의 중복작업을 제거 한다. mybatis에서는 프로그램에 있는 sql쿼리들을 한 구성파일에 구성하여 프로그램 코드와 sql을 분리할 수 있는 장점을 가지고 있다. 
resultType, resultClass 등 vo를 사용하지 않고 조회결과를 사용자 정의 dto, map 등으로 맴핑하여 사용할 수 있다.
기존 jdbc 프로그래밍의 경우 repository에서 곧바로 jdbc api 쪽으로 접근하여 db를 연결하였지만 위의 그림에 나와있듯이 mybatis를 사용할 경우 repository와 jdbc api 사이에 mybatis가 위치함으로써 편리한 access를 제공한다.
mybatis의 기능 구조
mybatis3의 databse access 프로세스
응용프로그램이 sqlsessionfactorybuilder를 위해 sqlsessionfactory를 빌드하도록 요청한다.
sqlsessionfactorybuilder는 sqlsessionfactory를 생성하기 위한 mybatis 구성 파일을 읽는다.
sqlsessionfactorybuilder는 mybatis 구성 파일의 정의에 따라 sqlsessionfactory를 생성한다.
클라이언트가 응용 프로그램에 대한 프로세스를 요청한다.
응용프로그램은 sqlsessionfactorybuilder를 사용하여 빌드된 sqlsessionfactory에서 sqlsession을 가져온다.
sqlsessionfactory는 sqlsession을 생성하고 이를 어플리케이션에 반환한다.
응용프로그램이 sqlsession에서 매퍼 인터페이스의 구현 개체를 가져온다.
응용프로그램이 매퍼 인터페이스 메서드를 호출한다
매퍼 인터페이스의 구현 개체가 sqlsession 메서드를 호출하고 sql 실행을 요청한다.
sqlsession은 매핑 파일에서 실행할 sql을 가져와 sql을 실행한다
mybatis-spring
sqlsessionfactorybean은 sqlsessionfactorybuilder를 위해 sqlsessionfactory를 빌드하도록 요청한다.
응용프로그램은 sqlsessionfactorybuilder를 사용하여 빌드된 sqlsessionfactory에서 sqlsession을 가져온다.
sqlsessionfactorybuilder는 mybatis 구성 파일의 정의에 따라 sqlsessionfactory를 생성한다. 따라서 생성된 sqlsessionfactory는 spring di 컨테이너에 의해 저장된다.
mapperfactorybean은 안전한 sqlsession(sqlsessiontemplate) 및 스레드 안전 매퍼 객체(mapper 인터페이스의 프록시 객체)를 생성한다. 따라서 생성되는 매퍼 객체는 spring di컨테이너에 의해 저장되며 서비스 클래스 등에 di가 적용된다. 매퍼 개체는 안전한 sqlsesseion(sqlsessiontemplate)을 사용하여 스레드 안전 구현을 제공한다.
클라이언트가 응용프로그램에 대한 프로세스를 요청한다.
어플리케이션은 di컨테이너에서 주입한 매퍼 개체(매퍼 인터페이스를 구현하는 프록시 개체)의 방법을 호출한다.
매퍼 객체는 호출된 메소드에 해당하는 sqlsession(sqlsessiontemplate) 메서드를 호출한다
sqlsession9sqlsessiontemplate)은 프록시 사용 및 안전한 sqlsession 메서드를 호출한다.
프록시 사용 및 스레드 안전 sqlsession은 트랜잭션에 할당된 mybatisw3 표준 sqlsession을 사용한다. 트랜잭션에 할당된 sqlsession이 존재하지 않는 경우 sqlsessionfactory 메서드를 호출하여 표준 mybatis3dml sqlsession을 가져온다
sqlsessionfactory는 mybatis3 표준 sqlsession을 반환한다. 반환된 mybatis3 표준 sqlsession이 트랜잭션에 할당되기 때문에 동일한 트랜잭션 내에 있는 경우 새 sqlsession을 생성하지 않고 동일한 sqlsession을 사용한다. on 메서드를 호출하고 sql 실행을 요청한다.
mybatis3 표준 sqlsession은 매핑 파일에서 실행할 sql을 가져와 실행한다.
이 mybatis-spring은 개발자가 sqlsessionfactorybean과 sqlsessiontemplate을 springbean 설정 파일에 bean으로 등록해야 한다. 참고로 springbean에는 db 연결 정보와 mybatis config 파일 정보, mapping 파일의 정보를 함께설정한다. 그리고 mybatis 설정 파일에는 vo 객체의 정보를 설정한다.
이 springbean 설정 파일은 datasouce정보와 mybatis config 파일 정보 mapping 파일의 정보를 읽고 sqlsessionfactorybean을 생성하고 이 sqlsessionfactorybean이 sqlsessionfactory를 생성하여 이 sqlsessionfactory가 sqlsession interface를 구현한 sqlsessiontemplate을 생성한다.
dbcp: 데이터베이스 커넥션 풀
커넥션 풀이란
hikariCP


# aop
aop는 관점지향프로그래밍으로 관점을 기준으로 다양한 기능을 분리하여 보는 프로그래밍이다. 관점(Aspect)이란, 부가 기능과 그 적용처를 정의하고 합쳐서 모듈로 만든 것이다.
aop의 목적
oop와 이름이 비슷하여 상반된 개념 같지만 관점지향프로그래밍은 객체지향 프로그래밍을 보완하기 위해 쓰인다. 기존 객체지향프로그래밍은 목적에 따라 클래스를 만들고 객체를 만들었다. 따라서 핵심 비즈니스 로직이든 부가 기능의 로직이든 하나의 객체로 분리하는데 그치고 이 기능들을 어떻게 바라보고 나눠쓸지에 대한 정의가 부족하다는 단점이 있다.
보통 비즈니스 웹 어플리케이션이라면 사업에 핵심적인 핵심 비즈니스 로직이 있고 어플리케이션 전체를 관통하는 부가 기능 로직이 있다. 종류는 로깅, 보안, 트랜잭션이 있다. 부가 기능 로직의 코드를 핵심 비즈니스 로직의 코드와 분리하여 코드의 간결성을 높이고 변경에 유연함과 무한한 확장이 가능하도록 하는 것이 aop의 목적이다.
aop의 적용방식
컴파일 시점 적용
컴파일 시점 적용 방식은 aspectj 컴파일러가 일반 .java 파일을 컴파일할 때 부가기능을 넣어서 .class 파일로 컴파일해주는 것을 의미한다. 이 동작을 aspect와 실제 코드를 연결하는 위빙이라고 부른다.
클래스 로딩 시점 적용
jvm 내 클래스로더에 .class 파일을 올리는 시점에 바이트 코드를 조작해 부가기능 로직을 추가하는 방식이다.
런타임 시점 적용
컴파일, 클래스 로딩, main()메서드의 실행 이후에 자바가 제공하는 범위 내에 부가 기능을 적용하는 방식이다. 이미 런타임 중이라 코드를 조작하기 어려워 스프링, 컨테이너, DI, 빈 등 여러 개념과 기능을 총동원하여 프록시를 통해 부가기능을 적용하는 방식이다.
프록시는 메서드 실행 시점에서만 다음 타겟을 호출할 수 있기 때문에 런타임 시점에 부가기능을 적용하는 방식은 메서드의 실행 지점으로 제한된다.
spring aop
spring aop는 런타임 시점에 적용하는 방식을 사용한다. 이유는 컴파일 시점과 클래스 로딩 시점에 적용하려면 별도의 컴파일러와 클래스로더 조작기를 써야 하는데 이것을 정하고 사용 및 유지하는 과정이 매우 어렵고 복잡하기 때문이다. 
aop 용어
aspect
joinpint
advice
pointcut
target
advisor

# 트랜잭션
트랜잭션은 db의 상태를 변경시키기 위해 수행하는 작업 단위이다.
db에서 하나의 거래를 안전하게 처리하도록 보장해주는 것이라고 할 수 있다.
데이터베이스를 변겨알 때의 커밋과 롤백을 시켜주는 최소의 단위를 말한다.
트랜잭션 기능
db가 제공하는 트랜잭션 기능을 사용하면 commit과 rollback으로 정상적인 작업이 가능하도록할 수 있다. 작업 중 하나라도 실패를 해서 거래 이전으로 되돌리는 것을 롤백이라고 한다.
그리고 모든 작업이 정상적으로 성공하는 경우 데이터베이스에 정상 반영하는 것을 커밋이라고 한다.
정리하자면 
데이터 변경 쿼리를 실행하고 데이터베이스에 그 결과를 반영하려면 커밋 명령어인 commit을 호출하고 결과를 반영하고 싶지 안흐면 롤백 명령어인 rollback을 호출하면 된다.
spring이 제공하는 transaction 핵심 기술
트랜잭션 동기화
jdbc를 이용하는 개발자가 직접 여러 개의 작업을 하나의 트랜잭션으로 관리하려면 connection 객체를 공유하는 등 상당히 불필요한 작업들이 많이 생긴다.
spring은 이러한 문제를 해결하고자 트랜잭션 동기화 기술을 제공하고 있다.
트랜잭션 동기화는 트랜잭션을 시작하기 위한 connection 객체를 특별한 저장소에 보관해두고 필요할 때 꺼내쓸 수 있도록 하는 기술이다. 
트랜잭션 동기화 저장소는 작업 쓰레드마다 connection 객체를 독립적으로 관리하기 때문에 멀티쓰레드 환경에서도 충돌이 발생할 여지가 없다. 
하지만 개발자가 jdbc가 아닌 hibernate와 같은 기술을 쓴다면 위의 jdbc 종속적인 트랜잭션 동기화 코드들은 문제를 유발하게 된다. 대표적으로 hibernate에서는 connection이 아닌 session이라는 객체를 사용하기 때문이다. 이러한 기술 종속적인 문제를 해결하기 위해 spring 트랜잭션 관리 부분을 추상화한 기술을 제공하고 있다. 
트랜잭션 추상화
spring은 트랜잭션 기술의 공통점을 담은 트랜잭션 추상화 기술을 제공하고 있다. 이를 이용함으로써 어플리케이션에 각 기술마다(jdbc, jpa, hibernate) 종속적인 코드를 이용하지 않고도 일관되게 트랜잭션을 처리할 수 있도록 해주고 있다.
spring이 제공하는 트랜잭션 경계 설정을 위한 추상 인터페이스는 platformTransactionManager이다. 예를 들어 만약 jdbc의 로컬 트랜잭션을 이용한다면 datasourceTxManager를 이용하면 된다.
이제 우리는 사용하는 기술과 무관하게 platformtransactionManager를 통해 다음의 코드와 같이 트랜잭션을 공유하고 커밋하고 롤백할 수 있게 되었다.
하지만 위와 같은 트랜잭션 관리 코드들이 비즈니스 로직 코드와 결합되어 2가지 책임을 갖고 있다. spring에서는 aop를 이용해 이러한 트랜잭션 부분을 핵심 비즈니스 로직과 분리하였다.
aop를 이용한 트랜잭션 분리
@Transactional 트랜잭션 어노테이션 사용
aop를 이용한 트랜잭션 분리는 어노테이션을 사용하고 트랜잭션 전파, 격리 수준, 제한 시간, 읽기 전용이라는 설정을 구현할 수 있다.

# git 정리
git config --global [user.name](http://user.name) “” : 현재 사용자의 모든 저장소 범위에서 user.name을 “”으로 설정. 이 명령어를 통해 커밋 이용자를 알 수 있음.
git config --global [user.email](http://user.email) “” : 위와 마찬가지
cf) local, system도 있음
git init : 현재 폴더를 git이 관리하는 작업 디렉토리로 설정
git remote add origin “” : origin이라는 이름으로 “” 주소를 원격 저장소로 지정하고 연결
git remote -v : 현재 로컬 저장소에 있는 원격 저장소를 나열해서 보여주는 명령어
git remote remove origin: origin이라는 원격저장소와의 연결을 해제하는 명령어
rd /s /q .git : .git 디렉터리 삭제. git init 취소하는 명령어
git branch : 브랜치 확인하는 명령어. 브랜치를 따로 생성 안 했을 때는 커밋까지 해야 브랜치명이 보임. 기본: master
git branch “” : 브랜치 생성하는 명령어
git checkout “” : 작업 브랜치를 해당 브랜치로 변경하는 명령어
git add : git 인덱스에 추가. 인덱스는 로컬 저장소에 커밋할 준비를 하기 위해 변경 내용을 임시로 저장하는 위치
git commit: 인덱스에 추가된 파일을 커밋. 로컬 저장소에 기록하는 방법
git log: 커밋 정보를 알려주는 명령어
git push origin master: origin이라는 원격저장소의 master라는 브랜치에 푸시하는 명령어
git pull: 원격 저장소의 내용을 로컬 브랜치로 가져오는 명령어


# 코드 설명

```java
@Component
@Aspect
@Slf4j
public class LoggerAspect {
	@Pointcut("execution(* bridge..controller.*Controller.*(..)) || execution(* bridge..service.*ServiceImpl.*(..)) || execution(* bridge..mapper.*Mapper.*(..))")
	private void loggerTarget() {}
	
	@Around("loggerTarget()")
	public Object logPrinter(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		log.debug(className + "." + methodName + "()");
		return joinPoint.proceed();
	}
}
```
loggerTarget() 이라는 이름으로 pointcut을 정의하는 메서드

@Around 어노테이션을 이용해 loggerTarget에서 지정한 포인트컷의 시기대로 타켓 메서드가 호출되기 전 후에 aop 즉 어드바이스를 적용. around는 어드바이스 어노테이션

정리

loggerAspect는 log를 기능별로 출력하고 싶은데 그럼 너무 비효율적이니깐 aop를 사용해 만든 클래스이다. loggerAspect 즉 aspect는 부가기능과 포인트컷이 지정된 클래스라고 보면된다. 부가기능 즉 advice는 joinpoint를 이용해 타켓 메서드가 호출될 때 실행될 메서드들을 정의하고 있다. 이때 어드바이스를 정의하는 어노테이션 즉 around는 advice가 타켓 메서드 호출에 따라 언제 실행이 될지를 정의하는 어노테이션인데 구체적으로 around는 타켓 메서드가 호출되기 전 후에 advice를 실행시키는 어노테이션이다. 이 부가기능에서는 주로 joinpoint 인터페이스의 getSignature메서드를 사용하는데 joinpoint를 이용해 타켓 메서드의 정보를 출력한다. 타켓 메서드의 클래스, 메서드 명을 출력한다. 그리고 return문을 이용해proceed 메서드를 호출하면서 다음 어드바이스 또는 타켓 메서드를 호출하는 기능으로 부가기능을 끝낸다.

원래 로그 사용할 때에는 logger 객체를 생성해서 사용해야 하는데 ( 이때 loggerFactory 이용) 그리고 관련 라이브러리를 임포트도 해줘야 함. 아무튼 이 과정을 롬복을 사용하는 경우 @Slf4j 어노테이션으로 생략할 수 있음. 자동으로 로거 생성됨.


@SpringbootApplication 어노테이션: 자동 설정을 해주기 위한 어노테이션. @SpringBootConfiguration @EnableAutoConfiguration @ComponentScan 으로 크게 3가지가 합쳐진 어노테이션이다. 스프링 부트 어플리케이션은 bean을 2번 등록한다. 처음에 componentScan으로 등록하고 EnableAutoConfiguration으로 추가적인 bean들을 읽어서 등록한다. @SpringBootConfiguration은 @Configuration의 하위 어노테이션으로 @Configuration과 동일한 역할을 수행한다. 이 어노테이션은 애플리케이션에 1개만 존재해야 하며 일반적으로 @SpringBootApplication에 포함되어있기 때문에 따로 작성할 필요는 없다. @EnableAutoConfiguration은 우리가 필요할 것 같은 빈들을 자동으로 설정해주는 자동 설정 기능을 활성화하는 어노테이션이다. @ComponentScan은 빈을 등록하기 위한 어노테이션들을 탐색하는 위치를 지정한다. 이 어노테이션이 붙은 클래스의 패키지를 스캔하여 컴포넌트(@Component, @Service, @Controller 등) 을 찾도록 지시한다. 참고로 @Configuration 어노테이션은 스프링 프레임워크에게 해당 클래스가 하나 이상의 빈을 제공하는 구성 클래스임을 나타내는 역할을 한다. 이 어노테이션은 빈을 정의하며 ( bean어노테이션을 사용하여 빈을 정의할 수 있다. bean 어노테이션은 해당 메서드가 생성하고 반환하는 객체를 스프링 컨테이너에 빈으로 등록한다.) 빈 간 의존성을 관리하고 환경 설정 및 외부 프로퍼티에도 사용한다 (애플리케이션의 환경 설정을 담당하는 데에도 사용된다. 프로퍼티 파일에서 값을 읽어오거나, 환경 변수를 통해 설정을 가져오는 등의 작업을 수행할 수 있다.). 


SpringApplication.run: 이 명령어는 많은 동작들을 수행하는데 그 중에서도 기억에 남는 건 내장 서버를 구동시킨다는 것이다. 이 메서드는 springboot의 내장된 웹서버 즉 WAS를 구동시켜 기본적인 설정을 적용하여 애플리케이션을 실행한다. 이때 WAS는 사용자가 요청을 보내면 특정 포트로 오는 요청을 잡아 SpringBoot 어플리케이션으로 연결시켜준다고 한다. 참고로 springboot에는 내장된 웹서버 즉 tomcat, jetty, undertow 중 하나의 서버를 사용할 수 있다. 따라서 개발자가 따로 웹서버?(Tomcat)을 설치할 필요가 없다.



RuleBasedTransactionAttribute.setName: transaction이 적용되는 메서드의 이름을 지정한다. jstg, 강의록에서는 *을 사용해 모든 메서드에 트랜잭션을 적용한다.

MatchAlwaysTransactionAttributeSource: 이 클래스는 항상 동일한 트랜잭션 속성을 반환하도록 구현되어 있습니다

@Autowired 어노테이션 : 필요한 의존 객체의 ‘타입’에 해당하는 빈을 찾아 주입한다. 생성자/setter/필드 이 3가지 경우에 Autowired를 사용한다. ioc 컨테이너에 등록되어 있는 참조할 빈을 찾아 주입한다.

@Bean 어노테이션: 우선 빈이란 스프링 ioc 컨테이너가 관리하는 객체로 의존성 관리를 위해 사용된다. 또한 이런 빈들을 싱글톤의 형태이다. 더 알아봐야겠지만 빈은 @Bean 어노테이션 뿐만 아니라 component 어노테이션 그 안에 controller service 어노테이션들을 통칭하는 것 같다. 그 중에 @Bean 어노테이션은 개발자가 컨트롤 할 수 없는 외부라이브러리를 빈으로 등록하고 싶은 경우에 쓰인다. 메서드로 return 되는 객체를 빈으로 등록한다고 한다.

autowired를 사용하여 appllicationContext를 주입하는 경우와 autowired를 사용하면서 생성자 또는 다른 필드를 사용하여 주입하는 경우는 다르다. 내가 이해한 바로는 전자는 스프링 컨테이너에서 직접 접근하여 필요한 빈을 가져올 때 사용하며 후자는 빈들 간의 의존성을 해결하기 위해 사용된다.



DatabaseConfiguration

applicationContext: ApplicationContext는 스프링 컨테이너라고 한다. Configuration이 붙은 클래스들을 설정 정보로 등록하고 @Bean 어노테이션이 붙은 메서드의 이름으로 빈들을 묶어 빈 목록을 생성한다. 클라이언트가 빈을 요청하면 getBean 함수를 통해 빈 목록에서 해당 빈을 찾고 그 빈을 갖고 있는 설정 클래스로부터 빈 생성을 요청하고 생성된 빈을 반환한다. 이때 빈은 싱글톤 규칙에 의해 생성되고 사용된다. 추가로 applicationContext 인터페이스의 구현체가 여러가지가 있는데 구현체에 따라 스프링 컨테이너를 xml을 기반으로 만들 수도 있고, 자바 클래스로 만들 수도 있다. 아무튼 아까의 설명보다 더 자세히 말하자면 스프링 컨테이너 내부에는 빈 저장소가 존재한다. 빈 저장소는 key로 빈 이름을 가지고 있으며 value로 실제 빈 객체를 가지고 있다. 내 생각인데 이때 key가 빈 어노테이션이 붙은 메서드 이름 같다. 또한 심화로 spring에서는 빈의 생성과 관계설정 같은 제어를 담당하는 ioc 컨테이너인 빈 팩토리가 존재한다. 근데 이 인터페이스 외에도 추가 기능이 필요한데 그 인터페이스가 빈 팩토리 인터페이스를 구현하는 applicationContext 인터페이스라고 한다. 상속 받아 확장한 이라고 설명이 되어 있어서 상속관계일 수도 암튼.

@Configuration: 위에 설명 자세히 되어 있음.

@PropertySource: 소스 코드를 작성하다 보면 정적으로 하드코딩된 내용을 쓸 때가 있다. 대표적으로 db의 설정정보가 그렇다. 하지만 이 방법은 변경사항이 있을 때 공유를 해야 하는 상황일 때 (민감한 정보까지 공유) 단점이 있다. 그래서 이를 해결하기 위해 설정 정보 파일을 따로 분리하고 불러오는 방식을 사용한다. 스프링 프로퍼티가 바로 그것이다. (jstg에서는 application.properties) 같다. 이 때 설정 정보 파일을 불러오는 방법이 propertySource이다. @Configuration 어노테이션과 같이 쓰인다. 내 생각인데 configuration 어노테이션이 빈 관련 역할도 하지만 설정 정보를 불러오는 역할도 하기 때문에 그런 것 같다.

@ConfigurationProperties: 이건 여러 프로퍼티를 가져와(보통 접두어를 사용하는 것 같다.) 하나의 클래스에 매핑 시켜 그 클래스 자체를 빈으로 등록할 때 쓰는 어노테이션이다. 이 프로퍼티들을 이용해 매핑 시켜 빈으로 등록한다는 점에서 PropertySouce 어노테이션과는 다르다.

BoardController

@Controller: 컨트롤러 클래스는 클라이언트와의 요청, 응답을 주고 받는 역할을 한다. 우선 내가 이해한 바로는 @Component 어노테이션은 개발자가 정의한 클래스(메서드가 아니라)를 스프링 빈으로 등록하고자 할 때 쓰는 어노테이션이다. Controller 어노테이션은 이 component 어노테이션의 하위 어노테이션으로 컨트롤러 클래스의 역할을 담은 어노테이션이다. 즉 사용자와의 요청과 응답을 제공하는 어노테이션으로 만약 component 어노테이션을 사용하려면 url을 연결해주는 @RequestMappling 어노테이션을 클래스 위에 추가적으로 써줘야 한다. 그리고 빈을 등록할 때는 key값과 value 값이 있는데 Controller 어노테이션은 key 값에 url 주소를 매핑하고 value에 빈으로 등록될 클래스 객체가 들어가는 것 같다. 그리고 추가적으로 bean 어노테이션은 메서드에 component 어노테이션에는 클래스 자체를 단위로 빈을 등록한다고 한다.

간단하게 말하면, **`@Component`** 어노테이션은 클래스 단위로 빈을 등록하는 데 사용되고, **`@Bean`** 어노테이션은 메서드 단위로 빈을 등록하는 데 사용됩니다. 일반적으로 **`@Component`**는 자동으로 스캔되는 빈을 생성할 때 사용되고, **`@Bean`**은 수동으로 빈을 정의하거나 설정할 때 사용됩니다.

@GetMapping: 우선 RequestMapping 어노테이션에 대해서 알아야 한다. RequestMapping 어노테이션은 http 메서드 요청을 처리할 수 있는 어노테이션이다. 이때 메서드 종류가 get, post, put, delete 등이 있다. 그래서 해당 메서드를 특정지어 쓰기 위해서는  method  속성?을 추가하여 메서드 방식을 지정해주어야 한다. 즉 RequestMapping 어노테이션은 이 모든 메서드를 포괄하는 http 요청을 수행하는 역할이라고 보면된다. 그럼 GetMapping 어노테이션은 자연스럽게 이 http 요청에서 해당 get 메서드 요청이 올 때 mapping 시켜주는 어노테이션이라고 보면 된다. 추가로 클래스 위에 RequestMapping으로 url이 설정되어 있고 메서드 안에 또 RequestMapping이나 GetMapping으로 url이 설정되어 있다면 이 url을 차례로 연결시킨 요청이 해당 메서드를 실행시킨다. 또한 RequestMapping에서 method를 지정하지 않을 수도 있는데 이럴 때는 해당하는 모든 메서드 요청에 그 클래스가 매핑이 된다.

ModelAndView: mvc에서 사용하는 클래스이다. 이 클래스는 사용자의 요청과 응답이 이루어지는 컨트롤러에서 사용되며 Model 클래스와 구분된다. 우선 Model 클래스는 데이터를 뷰에 전달하는 역할을 수행하며 이에 추가로 ModelAndView는 view의 정보까지 뷰에 전달하는 역할을 수행한다. 이때 데이터를 넘길 때는 model은 addAttribute() 메서드를 이용하며 modelandview는 addObject()메서드를 이용한다. 또한 modelandview에서 view를 전달할 때는 setViewName()메서드를 이용한다. 추가로 modelandview 객체를 생성할 때에는 인자로 뷰 경로를 입력해 뷰를 설정한 modelandview 객체를 생성할 수 있다.

return mv;  return “first/noticeWrite”; 쓰임 차이 정리: 이건 그냥 내 생각대로 해도 될 것 같다. 전자는 데이터와 뷰를 전달해 그 뷰에서 응답받아 modelandview 객체를 반환하는 거고 후자는 modelandview를 보통 사용하지 않고(modelandview는 view에 데이터 또는 뷰를 전달할 때 쓰는 것이기 때문에) 정적인 뷰를 반환할 때 사용하는 것 같다.

@RequestMapping: 위에 설명 있음

return “redirect:/openNoticeList.do”;: 내가 이해한 바로는 redirect는 보통 뷰를 반환하지 않을 때 사용하고 해당 메서드만 수행이 되면 redirect url을 다시 요청하여 그 url요청에 맞는 메서드를 다시 수행할 때 사용한다.

@RequestParam: 우선 이 어노테이션은 requestbody와 구분된다. 전자는 url에서 데이터를 받아오는 경우에 사용되고 requestbody는 그 이외에 보통 사용이 된다. requestbody도 url로 받을 수 있지만 변수 명을 구분해서 받을 수는 없다고 한다. 근데 마지막 이 말은 확실하지 않다.

@PostMapping: 위에 설명 있다.

List<>: 자바에서 사용하는 클래스 중 하나로 배열과 구분된다. 배열은 처음에 크기를 정적으로 지정하여 사용해야 하는 반면 list는 크기를 동적으로 생성시킬 수 있다. 즉 메모리가 허용하는 한 필요에 따라 크기를 또는 데이터를 추가할 수 있다.

@PathVariable: RequestParam과 동일한 기능을 하는데 requestParam과 달리 하나의 값만 받아올 수 있다고 한다. 근데 이 설명은 잘 모르겠다. 아닌 것 같은데? 나중에 더 알아보고 암튼 챗 지피티의 설명에 따르면 requestParam은 http 요청 파라미터를 추출하는데 사용되고 pathVariable은 url 템플릿의 경로 변수를 추출하는데 사용된다고 한다. 아직 차이 잘 모르겠다. 내가 이해한 바로는 requestparam은 파라미터로 가져오는 파라미터명 자체를 추출해서 사용하는 거고 pathvariable은 경로에 있는 변수 명을 이용해 변수 안의 실제 값을 추출해서 사용하는 것 같다.

CelebrityController

MultipartFile: multipart는 웹 클라이언트가 요청을 보낼 때, http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것이다. 웹 클라이언트가 서버에게 파일을 업로드할 때, http 프로토콜의 바디 부분에 파일정보를 담아서 전송을 하는데 파일을 한 번에 여러 개 전송을 하면 body 부분에 파일이 여러 개의 부분으로 연결되어 전송된다. 이렇게 여러 부분으로 나뉘어서 전송되는 것을 multipart data라고 한다. 보통 파일을 전송할 때 사용한다. multipartfile이란 사용자가 업로드한 file을 핸들러에서 손쉽게 다룰 수 있게 도와주는 매개변수 중 하나이다. 매개변수를 사용하기 위해서는 multipartresolver bean이 등록되어 있어야 한다. 이는 springboot에서 자동 등록을 지원하지만 springMVC에서는 기본으로 등록해주지 않으므로 꼭 확인해야 한다. multipartfile 인터페이스는 스프링에서 업로드한 파일을 표현할 때 사용되는 인터페이스이다. 이 인터페이스를 이용해서 업로드한 파일의 이름, 실제 데이터, 파일 크기 등을 구할 수 있다. 

HttpServletResponse: was가 웹브라우져로부터 서블렛요청을 받으면 요청을 받을 때 전달 받은 정보를 httpservletrequest 객체를 생성하여 저장.  웹브라우져에게 응답을 돌려줄 httpservletresponse 객체를 생성. 생성된 httpservletrequest(정보가 저장된)와 httpservletresponse(비어 있는)를 servlet에게 전달. httpservletrequest: http프로토콜의 request 정보를 서블릿에게 전달하기 위한 목적으로 사용. header 정보, parameter, cookie, url, uri 등의 정보를 읽어들이는 메서드를 가진 클래스. body의 stream을 읽어들이는 메서드를 가지고 있음. httpservletresponse: servlet은 httpservletresponse객체에 content type, 응답코드, 응답 메세지 등을 담아서 전송함.

다운로드 코드:

*@GetMapping*("/download.do") 

public void downloadFile(*@RequestParam* int fanIdx, HttpServletResponse response) throws Exception {

CelebrityDto fanDto = celebrityService.selectOneFanByFanId(fanIdx);

String fanImage = fanDto.getFimage();

FileInputStream fis = null;  //파일 경로나 file객체를 바이트 단위로 데이터를 읽기 위한 스트림이다.

BufferedInputStream bis = null;  //입력 스트림을 버퍼링하여 성능을 향상시키는 역할을 한다. 버퍼링으로 매핑하는 기능이기 때문에 단독으로 사용한다기보다는 다른 입력 스트림을 감싸서 사용한다. 버퍼는 데이터를 읽어올 때 일정량의 데이터를 한 번에 읽어와 메모리에 보관하고 이후에는 버퍼에서 데이터를 읽어오기 때문에 입출력 성능이 향상된다. 내 생각엔 버퍼가 속도가 빠르고 성능을 향상시켜주는 메모리영역이다. 검색해 봤더니 데이터를 일시적으로 저장하는 메모리 영역을 말한다.

BufferedOutputStream bos = null;   //출력스트림을 버퍼링하여 성능을 향상시키는 역할을 한다. 이것도 위와 마찬가지로 단독으로 사용한다기보다는 다른 출력 스트림을 감싸서 사용한다. 버퍼를 사용하므로 데이터를 출력할 때 일정량의 데이터를 한 번에 출력 버퍼에 담아두고 이후에는 한 번에 출력한다. 이로 인해 입출력 성능이 향상된다.

try {

response.setHeader("Content-Disposition", "inline;");    //http응답 헤더에 content-disposition 헤더를 설정하여 브라우저가 파일을 어떻게 처리해야 하는지 지정한다. 여기서는 inline으로 설정되어 있어 브라우저에서 파일을 바로 표시하도록 지시한다. 구체적으로 content-disposition 헤더는 서버에서 브라우저로 전송되는 콘텐츠의 처리 방식을 지정하는데 사용된다. 만약 inline이 아니라 attachment 값으로 설정하면 브라우저는 해당 콘텐츠를 다운로드하도록 지시하게 된다.

byte[] buf = new byte[1024];

fis = new FileInputStream(fanImage);   //파일 경로가 저장된 데이터를 아까 생성한 fis에 대입한다.

bis = new BufferedInputStream(fis);   // 속도와 성능을 향상시키기 위하여 fis를 bufferdinputstream을 이용해 버퍼로 감싼다.

bos = new BufferedOutputStream(response.getOutputStream()); response 객체의 출력스트림을 반환해 bufferedoutputstream을 이용해 버퍼로 감싸는 코드이다.

int read;

while((read = bis.read(buf, 0, 1024)) != -1) {          //bis 즉 fis에서 바이트?를 읽어와 read에 대입

bos.write(buf, 0, read);  //그 read를 bos 즉 response객체의 출력스트림에 write

}

} finally {

bos.close();

bis.close();

fis.close();

}

}

Dto

@Data 어노테이션: 일반적으로 자바 클래스 내의 일반적인 메소드를 자동으로 생성해주는 편리한 방법을 제공한다. 대표적으로 getter, setter 함수 등.

Mapper

@Mapper: mybatis와 같은 orm 프레임워크에서 주로 사용되는 어노테이션 중 하나이다. 이 어노테이션은 인터페이스에 부여되며 mybatis가 해당 인터페이스를 구현하는 클래스를 자동으로 생성하도록 지시한다. 

MyBatis는 SQL 쿼리와 자바 메소드를 매핑시키는 방식으로 데이터베이스와 상호작용합니다. 이를 위해 개발자는 SQL 쿼리를 포함하는 인터페이스를 작성하고, MyBatis는 이 인터페이스를 구현하는 클래스를 동적으로 생성하여 SQL 쿼리를 실행합니다.

**`@Mapper`** 어노테이션은 MyBatis에서 인터페이스를 매퍼(Mapper)로 표시하는 데 사용됩니다. MyBatis가 이 인터페이스를 읽고, 해당하는 SQL 쿼리를 실행할 수 있는 클래스를 생성합니다. 

MyBatis는 애플리케이션이 시작될 때 이러한 매퍼 인터페이스를 스캔하고, 해당하는 클래스를 동적으로 생성하여 SQL 쿼리를 실행할 준비를 합니다. 그러면 개발자는 이러한 인터페이스를 사용하여 데이터베이스와 상호작용할 수 있습니다.

ServiceImpl

@Service: 이 어노테이션은 해당 클래스가 비즈니스 로직을 담당하는 서비스 클래스임을 나타낸다. 주로 서비스계층에서 사용되며 해당 클래스가 비즈니스 로직을 처리하고 트랜잭션 관리, 예외 처리 등의 역할을 담당한다.

@Override: 이 어노테이션은 자바 프로그래밍에서 메소드를 재정의할 때 사용되는 어노테이션이다. 

@Value(”${application.upload-path}”): 외부 프로퍼티 값을 가져와서 uploadPath라는 변수에 할당하는 코드이다. @Value 어노테이션은 스프링 프레임워크에서 외부 설정 파일의 값을 가져와서 변수에 주입하는데 사용된다. @PrpertySource 어노테이션은 스프링 구성 클래스에서 외부 프로퍼티 파일을 지정하는데 사용된다. 이 어노테이션을 사용하면 스프링 애플리케이션 컨텍스트에 외부 프로퍼티 파일을 로드할 수가 있다. @PropertySource를 사용하여 지정된 파일은 스프링 환경에 추가되고 @Value 어노테이션을 사용하여 프로퍼티 파일에서 값을 주입할 수 있다. 즉 @PropertySource는 외부 프로퍼티 파일을 스프링 환경에 로드하는데 사용되고 @Value는 외부 프로퍼티 파일의 값을 스프링 빈에 주입하는데 사용된다.

https://s.gravatar.com/avatar/f4304bca83085a0e7271bd0afa284bde?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fdl.png

**You**

그럼 @PropertySource("classpath:application.properties") 
이 코드가 있어야
 @Value("${application.upload-path}")
    private String uploadPath;
이 코드를 쓸 수 있는 거야?

ChatGPT

**ChatGPT**

네, 맞습니다. **`@PropertySource`** 어노테이션을 사용하여 외부 프로퍼티 파일을 로드한 후에 **`@Value`** 어노테이션을 사용하여 해당 프로퍼티 파일에서 값을 주입할 수 있습니다.

**`@PropertySource`** 어노테이션은 외부 프로퍼티 파일을 스프링 환경에 로드하는 역할을 합니다. 그리고 **`@Value`** 어노테이션은 프로퍼티 파일에서 특정 값을 가져와서 해당 필드에 주입하는 역할을 합니다.

따라서 **`@PropertySource`** 어노테이션을 사용하여 프로퍼티 파일을 로드한 후에 **`@Value`** 어노테이션을 사용하여 해당 프로퍼티 파일에서 값을 사용할 수 있습니다.

예를 들어:

```java
javaCopy code
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${application.upload-path}")
    private String uploadPath;

    // uploadPath 변수에 외부 프로퍼티 파일의 값을 주입합니다.
}

```

위의 코드에서 **`@PropertySource`** 어노테이션을 사용하여 클래스패스의 **`application.properties`** 파일을 스프링 환경에 로드한 후에, **`@Value`** 어노테이션을 사용하여 해당 파일에서 **`application.upload-path`** 키에 해당하는 값을 가져와서 **`uploadPath`** 변수에 주입합니다.

업로드 코드: 이해완료

sql 쿼리

<!**DOCTYPE** mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"

"https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace=””> </mapper>: 매퍼 파일의 루트 엘리먼트이다. 이 엘리먼트는 매퍼 파일 전체의 시작과 끝을 나타낸다. 또한 이 엘리먼트의 namespace 속성을 사용하여 해당 매퍼에 대한 namespace를 지정한다. namespace 속성은 해당 매퍼 파일이 연결될 자바 인터페이스의 경로를 지정한다. 이 경로는 mybatis 설정 파일에 등록되어 있는 매퍼 인터페이스와 연결된다. 이를 통해 mybatis는 해당 인터페이스에 정의된 메소드와 매퍼 파일에 정의된 sql 쿼리를 매핑한다.

templates

thymeleaf -#lists

form - action, method, id

form

<form action="요청을 전달할 서버의 페이지" method="요청방식" enctype="전달하는 데이터의 형식">

method - get, post. 두 가지가 있다.

get 방식: 주소창에 파라미터가 노출되어 전달되는 방식. http:// www.naver.com?code=154464. 여기 뒤에 ?파라미터 이름= 파라미터 값(=쿼리스트링).

post 방식: 주소창에 파라미터가 넘어가는 방식이 아니라 http 프로토콜의 body에 파라미터가 넘어가는 방식(확인: 개발자도구 network탭에 headers의 (body 부분?) Form Data 부분). 즉, 눈에 보이지 않아 보안성이 유지되므로 주로 로그인 페이지, 회원가입 페이지, 글 작성페이지 등에 사용된다. 용량 제한이 있는 get방식과 다르게 사용자의 입력을 표준 입력으로 넘겨주기 때문에 입력 내용의 길이의 제한을 받지 않는다.

enctype = multipart/form-data, application/x-www-form-urlcencoded, text/plain. 세 가지가 있다.

multipart/form-data : 파일(<input type=”file”>)이 포함된 폼을 전송할 때 사용. 그렇게 하지 않으면 웹 서버로 데이터를 넘길 때 파일의 경로명만 전송되고 파일 내용이 전송되지 않기 때문이다. 이때 메소드는 post값으로 지정해줘야 한다. 파일 업로드가 없는 폼에도 사용 가능. 다만 multipart/form-data는 파일 전송을 위해 추가적으로 표시되는 요소들이 있기 때문에 application/x-www-form-urlccencoded 보다 기본 데이터가 더 커지기 때문에 짧은 데이터 전송에는 비효율적이다.

application/www-form-urlencoded: 디폴트 값이다. enctype을 따로 설정하지 않으면 이 값이 설정된다. 파일이 없는 폼에 사용. 인코딩한 문자열을 get 방식으로 전송하는 것과 동일한 전송 데이터를 생성하며, post 방식으로 전송하는 차이만 있다. 폼데이터는 서버로 전송되기 이전에 url-encode된다.

text/plain: 인코딩을 하지 않은 문자 상태로 전송한다.





 
