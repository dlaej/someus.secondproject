# mybatis 공부

jdbc는 자바 응용프로그램과 데이터베이스를 연결해주는 자바 api이다.

그러나 이 jdbc는 로우레벨의 api이기도 하고 단점이 많기 때문에 mybatis 프레임워크를 사용한다.

mybatis프레임워크는 orm프레임워크로서 object relational mapping의 약자로 자바 객체와 sql문을 매핑시켜주는 프레임워크다. 

이 프레임워크는 jdbc를 사용하여 데이터베이스와 연결을 하며 매핑을 쉽게 해주는 대표적인 자바 응용  프로그램의 프레임워크이다. 

mybatis에는 mybatis3과 mybatis-spring이 있다. 

우선 mybatis3은 sqlsessionfactorybuilder를 이용하여 mybatis config file을 통해 설정 정보를 읽어와 sqlsessionfactory를 생성한다. 그 이후 응용프로그램의 요청 시 sqlsessionfactory는 sqlsession을 생성하고 이 sqlsession 객체는 mapper interface를 구현하고 응용프로그램은 sqlsession을 통해 구현체의 메서드를 호출하면 sqlsession이 sql 쿼리를 실행한다. 이때 자바 객체와의 연결은 sql쿼리의 parameterType과 resultType을 통해 매핑된다. 

그러나 이 mybatis3은 thread-safe하지 않기 때문에 thread마다 필요에 따라 생성되는 단점이 있다. (sqlsession이?)

이 단점을 보완해준 것이 mybatis-spring이다. 이건 내 추측인데 이 프레임워크가 spring의 싱글톤 특성이 담긴 프레임워크인 것 같다. 

이 mybatis-spring은 우선 개발자가 sqlsessionfactorybean과 sqlsessiontemplate을 springbean 설정 파일에 bean으로 등록해야 한다. (someus프로젝트에서는 databaseconfiguration에 sqlsessionfactory를 bean으로 등록함. 왜 여기에 한 번에 하고 factorybean이 아니라 factory에다가 해주는 거지?) 

참고로 springbean에는 db연결 정보와 mybatis config 파일정보, mapping 파일의 정보를 함께 설정한다. 그리고 mybatis 설정 파일에는 vo 객체의 정보를 설정한다.

someus 프로젝트는 이 설정 파일 모두를 databaseconfiguration에 해줌. db와의 연결은 application.properties에.

아무튼 이 springbean 설정파일은 datasource 정보와 mybatis config 파일 정보 mapping 파일의 정보를 읽고 sqlsessionfactorybean을 생성하고(생성은 모르겠음. 근데 springbean 설정 파일에 이 sqlsessionfactorybean과 sqlsessiontemplate을 빈으로 설정함)

이 sqlsessionfactorybean이 sqlsessionfactory를 생성하여 이 sqlsessionfactory가 sqlsession interface를 구현한 sqlsessiontemplate을 생성한다. 

이 sqlsessiontemplate은 thread-safe 하기 때문에 객체를 여러 번 만들지 않는다. 멀티스레드 구조에서 유용하다. 그리고 여기서 내가 본 포인트는 mybatis3은 sqlsession이 인터페이스가 아닌 것 같은데 mybatis-spring은 sqlsession이 인터페이스고 sqlsessiontemplate이 이 인터페이스를 구현한다는 것이다. 

암튼 이 sqlsessiontemplate이 sql쿼리를 실행하는 역할을 한다. 

여기서 또 중요한 개념이 db와 응용프로그램을 실제로 연결해주는 datasource인데 커넥션풀이라는 개념을 통해 연결을 한다. 

원래 db와 connection을 할 때는 커넥션을 조회하고 연결하고 부가 정보를 전달하고 인증을하고 세션을 생성해서 커넥션 생성을 완료 하며 커넥션을 반환하는 복잡한 과정을 가지고 있다. 

이때 요청이 올 때마다 이 과정을 반복하는 것은 매우 비효율적이라 나온 개념이 커넥션 풀이다. 

이 커넥션 풀은 커넥션 풀이라는 구조 안에 이미 연결이 되어진 connection을 미리 많이 만들어놓고 응용 프로그램이 요청을 할 때 이 connection을 획득하게 해주는 개념이다. 만약 커넥션을 모두 사용하고 나면 커넥션을 끊는 것이 아니라 다른 요청에도 사용가능하도록 해당 커넥션을 그대로 커넥션 풀에 반납한다. 

이 과정을 담은 오픈 소스가 hikari이다. 

datasource는 좀 복잡하게 다루면 매번 새로운 connection을 생성하는 방법과 커넥션 풀을 이용해 connection을 미리 만들어두는 방법을 개발자가 쉽게 전환 수 있도록 해주는 인터페이스이다. 즉 이 datasource는 커넥션을 획득하는 방법을 추상화하는 인터페이스이다. 핵심 기능은 커넥션 조회 뿐이다. 

암튼 이 인터페이스를 가지고 이 인터페이스를 구현한  hikaricp같은 라이브러리를 이용해 db와 실제로 연결을 한다. 

참고로 mybatisconfig 파일에 vo 객체의 정보를 typealiases를 이용해 설정한다고 했는데 이 과정은 생략 가능. 클래스명이랑 패키지명이랑 충돌을 안하면? 이건 뭔지 모르겠는데 중요한 건 아닌 것 같음. 아무튼.

# aop
aop란 관점지향프로그래밍으로 핵심 비즈니스 로직과 부가적인 로직을 분리해 핵심 비즈니스 로직에 부가적인 로직을 필요한 곳 마다 추가하는 프로그래밍기법이다.

용어로는 aspect, advice, joinpoint, pointcut, target, proxy, weaving 등이 있다.

차례로 aspect는 공통적으로 적용될 기능을 얘기한다. 부가적인 기능을 정의한 코드인 어드바이스와 어드바이스를 어느 곳에 적용할지 결정하는 포인트컷의 조합으로 만들어진다.

내가 이해한 바로는 그냥 핵심 비즈니스 로직에 적용될 공통 기능을 말하는 것 같다.

advice란 실제로 부가적인 기능을 구현한 객체를 의미한다.

joinpoint란 내가 이해한 바로는 어드바이스가 적용될 위치의 후보들이라는 느낌이다.

pointcut은 실제로 어드바이스가 적용될 위치를 설정하는 식을 의미하는 것 같다. 

어드바이스를 적용할 조인 포인트를 선별하는 과정이나 그 기능을 정의한 모듈을 의미한다고 한다.

타켓은 실제로 비즈니스 로직을 수행하는 객체를 의미한다고 한다. 즉 어드바이스를 적용할 대상이다.

프록시는 어드바이스가 적용되었을 때 생성되는 객체를 의미한다.

어드바이스 안에는 다섯 개의 어노테이션이 있다. 그 중에 하나가 @Around 어노테이션.

조인포인트와 조인포인트 객체 안의 signature를 이용해 실행되고 있는? 메서드나 클래스의 정보를 알 수 있다.

proceedingjoinpoint 인터페이스는 joinpoint 인터페이스를 상속받는다.


# 트랜잭션
트랜잭션이랑 데이터베이스를 변경할 때의 커밋과 롤백을 시켜주는 최소의 단위를 말하는 것 같다.

spring이 제공하는 트랜잭션은 3가지 핵심 기술이 있다. 

트랜잭션 동기화, 트랜잭션 추상화, aop를 이용한 트랜잭션 분리가 그것이다.

트랜잭션 동기화는 트랜잭션을 이용하려고 할 때 복잡한 과정들이 생기는데 이를 방지하고자 트랜잭션을 어떠한 공간에 미리 만들어놓고 필요할 때마다 쓰는 기술이다.

그런데 이러한 트랜잭션이 spring에서 동작될 때 데이터베이스와 긴밀한 연결을 하게 되는데 해당 코드가 jdbc를 사용할 수도 hibernate나 jta를 사용할 수도 있다. 이때 어떤 것을 쓰느냐에 따라 코드가 종속적으로 코딩이 될 수 있는데 이 단점을 해결하고자 platformtransactionmanager라는 인터페이스를 두어 추상화를 구현했다.

aop를 이용한 트랜잭션 분리는 어노테이션을 사용하고 트랜잭션 전파, 격리 수준, 제한 시간, 읽기 전용이라는 설정을 구현할 수 있다.




spring은 트랜잭션에 관해 3가지 기술을 제공한다.

transaction은 여러 개의 작업을 하나의 트랜잭션으로 관리하려면 작업마다의 connection을 서로 공유해야 한다. 이 과정 자체가 복잡하므로 spring은 트랜잭션 동기화 기술을 지원한다. 이 기술은 트랜잭션을 위한 connection을 특별한 저장소에 저장해두었다가 필요할 때 꺼내 쓸 수 있는 기술이다. 그러나 이 기술은 서로 다른 api (jdbc, jpa)들끼리 방법이 각자 다르다. 그렇기 때문에 이 기술들을 추상화 할 수 있는 인터페이스가 필요하다. 그게 바로 PlatformTransactionManager. 이게 바로 트랜잭션 추상화이다. 그러나 이 또한 비즈니스 로직과 트랜잭션과 같은 부가 로직이 구분되지 않는 문제점이 있다. 이를 해결하기 위해 spring이 지원하는 기술이 aop이다.  이를 적용한 어노테이션이 바로 transactional.

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
package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SomeusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SomeusApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
```
@SpringbootApplication 어노테이션: 자동 설정을 해주기 위한 어노테이션. @SpringBootConfiguration @EnableAutoConfiguration @ComponentScan 으로 크게 3가지가 합쳐진 어노테이션이다. 스프링 부트 어플리케이션은 bean을 2번 등록한다. 처음에 componentScan으로 등록하고 EnableAutoConfiguration으로 추가적인 bean들을 읽어서 등록한다. @SpringBootConfiguration은 @Configuration의 하위 어노테이션으로 @Configuration과 동일한 역할을 수행한다. 이 어노테이션은 애플리케이션에 1개만 존재해야 하며 일반적으로 @SpringBootApplication에 포함되어있기 때문에 따로 작성할 필요는 없다. @EnableAutoConfiguration은 우리가 필요할 것 같은 빈들을 자동으로 설정해주는 자동 설정 기능을 활성화하는 어노테이션이다. @ComponentScan은 빈을 등록하기 위한 어노테이션들을 탐색하는 위치를 지정한다. 이 어노테이션이 붙은 클래스의 패키지를 스캔하여 컴포넌트(@Component, @Service, @Controller 등) 을 찾도록 지시한다. 참고로 @Configuration 어노테이션은 스프링 프레임워크에게 해당 클래스가 하나 이상의 빈을 제공하는 구성 클래스임을 나타내는 역할을 한다. 이 어노테이션은 빈을 정의하며 ( bean어노테이션을 사용하여 빈을 정의할 수 있다. bean 어노테이션은 해당 메서드가 생성하고 반환하는 객체를 스프링 컨테이너에 빈으로 등록한다.) 빈 간 의존성을 관리하고 환경 설정 및 외부 프로퍼티에도 사용한다 (애플리케이션의 환경 설정을 담당하는 데에도 사용된다. 프로퍼티 파일에서 값을 읽어오거나, 환경 변수를 통해 설정을 가져오는 등의 작업을 수행할 수 있다.). 

```java
package project.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggerAspect {
	@Pointcut("execution(* someus..controller.*Controller.*(..)) || execution(* someus..service.*ServiceImpl.*(..)) || execution(* someus..mapper.*Mapper.*(..))")
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
loggerAspect는 log를 기능별로 출력하고 싶은데 그럼 너무 비효율적이니깐 aop를 사용해 만든 클래스이다. loggerAspect 즉 aspect는 부가기능과 포인트컷이 지정된 클래스라고 보면된다. 부가기능 즉 advice는 joinpoint를 이용해 타켓 메서드가 호출될 때 실행될 메서드들을 정의하고 있다. 이때 어드바이스를 정의하는 어노테이션 즉 around는 advice가 타켓 메서드 호출에 따라 언제 실행이 될지를 정의하는 어노테이션인데 구체적으로 around는 타켓 메서드가 호출되기 전 후에 advice를 실행시키는 어노테이션이다. 이 부가기능에서는 주로 joinpoint 인터페이스의 getSignature메서드를 사용하는데 joinpoint를 이용해 타켓 메서드의 정보를 출력한다. 타켓 메서드의 클래스, 메서드 명을 출력한다. 그리고 return문을 이용해proceed 메서드를 호출하면서 다음 어드바이스 또는 타켓 메서드를 호출하는 기능으로 부가기능을 끝낸다.


```java
package project.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {

	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* someus..service.*Impl.*(..))";
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public TransactionInterceptor transactionAdvice() {
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		transactionAttribute.setRollbackRules(
				Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);	
	}
	
	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());		
	}
}
```
RuleBasedTransactionAttribute.setName: transaction이 적용되는 메서드의 이름을 지정한다. jstg, 강의록에서는 *을 사용해 모든 메서드에 트랜잭션을 적용한다.

MatchAlwaysTransactionAttributeSource: 이 클래스는 항상 동일한 트랜잭션 속성을 반환하도록 구현되어 있습니다

@Autowired 어노테이션 : 필요한 의존 객체의 ‘타입’에 해당하는 빈을 찾아 주입한다. 생성자/setter/필드 이 3가지 경우에 Autowired를 사용한다. ioc 컨테이너에 등록되어 있는 참조할 빈을 찾아 주입한다.

@Bean 어노테이션: 우선 빈이란 스프링 ioc 컨테이너가 관리하는 객체로 의존성 관리를 위해 사용된다. 또한 이런 빈들을 싱글톤의 형태이다. 더 알아봐야겠지만 빈은 @Bean 어노테이션 뿐만 아니라 component 어노테이션 그 안에 controller service 어노테이션들을 통칭하는 것 같다. 그 중에 @Bean 어노테이션은 개발자가 컨트롤 할 수 없는 외부라이브러리를 빈으로 등록하고 싶은 경우에 쓰인다. 메서드로 return 되는 객체를 빈으로 등록한다고 한다.

autowired를 사용하여 appllicationContext를 주입하는 경우와 autowired를 사용하면서 생성자 또는 다른 필드를 사용하여 주입하는 경우는 다르다. 내가 이해한 바로는 전자는 스프링 컨테이너에서 직접 접근하여 필요한 빈을 가져올 때 사용하며 후자는 빈들 간의 의존성을 해결하기 위해 사용된다.


```java
package project.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(
			applicationContext.getResources("classpath:/mapper/**/sql-*.xml")
		);
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}

```

applicationContext: ApplicationContext는 스프링 컨테이너라고 한다. Configuration이 붙은 클래스들을 설정 정보로 등록하고 @Bean 어노테이션이 붙은 메서드의 이름으로 빈들을 묶어 빈 목록을 생성한다. 클라이언트가 빈을 요청하면 getBean 함수를 통해 빈 목록에서 해당 빈을 찾고 그 빈을 갖고 있는 설정 클래스로부터 빈 생성을 요청하고 생성된 빈을 반환한다. 이때 빈은 싱글톤 규칙에 의해 생성되고 사용된다. 추가로 applicationContext 인터페이스의 구현체가 여러가지가 있는데 구현체에 따라 스프링 컨테이너를 xml을 기반으로 만들 수도 있고, 자바 클래스로 만들 수도 있다. 아무튼 아까의 설명보다 더 자세히 말하자면 스프링 컨테이너 내부에는 빈 저장소가 존재한다. 빈 저장소는 key로 빈 이름을 가지고 있으며 value로 실제 빈 객체를 가지고 있다. 내 생각인데 이때 key가 빈 어노테이션이 붙은 메서드 이름 같다. 또한 심화로 spring에서는 빈의 생성과 관계설정 같은 제어를 담당하는 ioc 컨테이너인 빈 팩토리가 존재한다. 근데 이 인터페이스 외에도 추가 기능이 필요한데 그 인터페이스가 빈 팩토리 인터페이스를 구현하는 applicationContext 인터페이스라고 한다. 상속 받아 확장한 이라고 설명이 되어 있어서 상속관계일 수도 암튼.

@Configuration: 위에 설명 자세히 되어 있음.

@PropertySource: 소스 코드를 작성하다 보면 정적으로 하드코딩된 내용을 쓸 때가 있다. 대표적으로 db의 설정정보가 그렇다. 하지만 이 방법은 변경사항이 있을 때 공유를 해야 하는 상황일 때 (민감한 정보까지 공유) 단점이 있다. 그래서 이를 해결하기 위해 설정 정보 파일을 따로 분리하고 불러오는 방식을 사용한다. 스프링 프로퍼티가 바로 그것이다. (jstg에서는 application.properties) 같다. 이 때 설정 정보 파일을 불러오는 방법이 propertySource이다. @Configuration 어노테이션과 같이 쓰인다. 내 생각인데 configuration 어노테이션이 빈 관련 역할도 하지만 설정 정보를 불러오는 역할도 하기 때문에 그런 것 같다.

@ConfigurationProperties: 이건 여러 프로퍼티를 가져와(보통 접두어를 사용하는 것 같다.) 하나의 클래스에 매핑 시켜 그 클래스 자체를 빈으로 등록할 때 쓰는 어노테이션이다. 이 프로퍼티들을 이용해 매핑 시켜 빈으로 등록한다는 점에서 PropertySouce 어노테이션과는 다르다.



@Controller: 컨트롤러 클래스는 클라이언트와의 요청, 응답을 주고 받는 역할을 한다. 우선 내가 이해한 바로는 @Component 어노테이션은 개발자가 정의한 클래스(메서드가 아니라)를 스프링 빈으로 등록하고자 할 때 쓰는 어노테이션이다. Controller 어노테이션은 이 component 어노테이션의 하위 어노테이션으로 컨트롤러 클래스의 역할을 담은 어노테이션이다. 즉 사용자와의 요청과 응답을 제공하는 어노테이션으로 만약 component 어노테이션을 사용하려면 url을 연결해주는 @RequestMappling 어노테이션을 클래스 위에 추가적으로 써줘야 한다. 그리고 빈을 등록할 때는 key값과 value 값이 있는데 Controller 어노테이션은 key 값에 url 주소를 매핑하고 value에 빈으로 등록될 클래스 객체가 들어가는 것 같다. 그리고 추가적으로 bean 어노테이션은 메서드에 component 어노테이션에는 클래스 자체를 단위로 빈을 등록한다고 한다.

간단하게 말하면, **`@Component`** 어노테이션은 클래스 단위로 빈을 등록하는 데 사용되고, **`@Bean`** 어노테이션은 메서드 단위로 빈을 등록하는 데 사용됩니다. 일반적으로 **`@Component`**는 자동으로 스캔되는 빈을 생성할 때 사용되고, **`@Bean`**은 수동으로 빈을 정의하거나 설정할 때 사용됩니다.

@GetMapping: 우선 RequestMapping 어노테이션에 대해서 알아야 한다. RequestMapping 어노테이션은 http 메서드 요청을 처리할 수 있는 어노테이션이다. 이때 메서드 종류가 get, post, put, delete 등이 있다. 그래서 해당 메서드를 특정지어 쓰기 위해서는  method  속성?을 추가하여 메서드 방식을 지정해주어야 한다. 즉 RequestMapping 어노테이션은 이 모든 메서드를 포괄하는 http 요청을 수행하는 역할이라고 보면된다. 그럼 GetMapping 어노테이션은 자연스럽게 이 http 요청에서 해당 get 메서드 요청이 올 때 mapping 시켜주는 어노테이션이라고 보면 된다. 추가로 클래스 위에 RequestMapping으로 url이 설정되어 있고 메서드 안에 또 RequestMapping이나 GetMapping으로 url이 설정되어 있다면 이 url을 차례로 연결시킨 요청이 해당 메서드를 실행시킨다. 또한 RequestMapping에서 method를 지정하지 않을 수도 있는데 이럴 때는 해당하는 모든 메서드 요청에 그 클래스가 매핑이 된다.


@RequestParam: 우선 이 어노테이션은 requestbody와 구분된다. 전자는 url에서 데이터를 받아오는 경우에 사용되고 requestbody는 그 이외에 보통 사용이 된다. requestbody도 url로 받을 수 있지만 변수 명을 구분해서 받을 수는 없다고 한다. 근데 마지막 이 말은 확실하지 않다.



@PathVariable: RequestParam과 동일한 기능을 하는데 requestParam과 달리 하나의 값만 받아올 수 있다고 한다. 근데 이 설명은 잘 모르겠다. 아닌 것 같은데? 나중에 더 알아보고 암튼 챗 지피티의 설명에 따르면 requestParam은 http 요청 파라미터를 추출하는데 사용되고 pathVariable은 url 템플릿의 경로 변수를 추출하는데 사용된다고 한다. 아직 차이 잘 모르겠다. 내가 이해한 바로는 requestparam은 파라미터로 가져오는 파라미터명 자체를 추출해서 사용하는 거고 pathvariable은 경로에 있는 변수 명을 이용해 변수 안의 실제 값을 추출해서 사용하는 것 같다.


MultipartFile: multipart는 웹 클라이언트가 요청을 보낼 때, http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것이다. 웹 클라이언트가 서버에게 파일을 업로드할 때, http 프로토콜의 바디 부분에 파일정보를 담아서 전송을 하는데 파일을 한 번에 여러 개 전송을 하면 body 부분에 파일이 여러 개의 부분으로 연결되어 전송된다. 이렇게 여러 부분으로 나뉘어서 전송되는 것을 multipart data라고 한다. 보통 파일을 전송할 때 사용한다. multipartfile이란 사용자가 업로드한 file을 핸들러에서 손쉽게 다룰 수 있게 도와주는 매개변수 중 하나이다. 매개변수를 사용하기 위해서는 multipartresolver bean이 등록되어 있어야 한다. 이는 springboot에서 자동 등록을 지원하지만 springMVC에서는 기본으로 등록해주지 않으므로 꼭 확인해야 한다. multipartfile 인터페이스는 스프링에서 업로드한 파일을 표현할 때 사용되는 인터페이스이다. 이 인터페이스를 이용해서 업로드한 파일의 이름, 실제 데이터, 파일 크기 등을 구할 수 있다. 

HttpServletResponse: was가 웹브라우져로부터 서블렛요청을 받으면 요청을 받을 때 전달 받은 정보를 httpservletrequest 객체를 생성하여 저장.  웹브라우져에게 응답을 돌려줄 httpservletresponse 객체를 생성. 생성된 httpservletrequest(정보가 저장된)와 httpservletresponse(비어 있는)를 servlet에게 전달. httpservletrequest: http프로토콜의 request 정보를 서블릿에게 전달하기 위한 목적으로 사용. header 정보, parameter, cookie, url, uri 등의 정보를 읽어들이는 메서드를 가진 클래스. body의 stream을 읽어들이는 메서드를 가지고 있음. httpservletresponse: servlet은 httpservletresponse객체에 content type, 응답코드, 응답 메세지 등을 담아서 전송함.


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
