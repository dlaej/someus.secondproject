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
sql
mybatis의 기능 구조
dbcp: 데이터베이스 커넥션 풀
커넥션 풀이란
hikariCP


# aop



# 트랜잭션


# git 정리



# 코드 설명


