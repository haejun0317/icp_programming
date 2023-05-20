# Composing Method
- 메서드를 적절하게 정리

### Extract Method
- 메서드의 역할이 여러가지라서 길이가 긴 경우
- 메서드는 SRP를 가져야 함
- 메서드 내부에서 또 다른 메서드를 분리해 낼 수 있는 경우
- 메서드 행위의 추상화 수준을 맞춘다

> order.create 메서드 분리

### Replace Temp with Query
- 임시변수를 메서드 호출로 바꾸기
- 메서드로 변경 시 필요한 곳에서 재사용 가능. 코드 중복 제거 가능
- 메서드로 만들어서 의미를 명확하게 함

> total price 나 적립률 계산 로직 메서드로


# Moving Features between Objects
- 객체 설계 시 '기능을 어디에 넣을까'. 클래스 정리 기법

### Move Method
- 메서드가 자신의 클래스보다 다른 클래스의 인스턴스 변수나 기능을 더 많이 이용할 때
- 클래스의 기능이 너무 많거나 다른 클래스에 의존성이 높을때

### Move Field
- 인스턴스 변수가 자신의 클래스가 아니라 다른 클래스에서 더 많이 쓰이는 경우
- put a field in the same place as the methods that use it (or else where most of these methods are)

### Extract Class
- 클래스가 SRP을 위반한 경우 클래스 분리
- 클래스에 Manager, Control 과 같은 모호한 표현 사용하지 않음. 여러 일을 처리하게 될 확률 높음

> OrderService에 적립률 조회하는 기능이 있거나

### Inline Class
- Extract Class 와 반대
- 기능을 대부분 이동시켜 남는 기능이 거의 없을 때

> Lazy Class와 관련?

### Hide Delegate
- Server is the object to which the client has direct access. -> Person
- Delegate is the end object that contains the functionality needed by the client. -> Department
- 클라이언트가 직접 접근할 수 있게 하면 객체간 relation 이 다 노출됨
- 중개 메서드가 많아지면 Middle Man 코드스멜
```
## relation이 다 드러남
Person manager = john.getDepartment().getManager();

## 위임 메서드 생성
public Person getManager() {
    return this.department.getManager();
}

manager = john.getManager();
```

> OrderItem에 일일이 접근하지 않고 totalPrice를 order에 관리하는 것이 해당되나
아니면 적립률

### Remove Middle Man
- Hide Delegate 와 반대
- 대리객체 기능이 추가될 때마다 위임 메서드 추가해줘야 함

### Introduce Foreign Method
- 유틸 클래스에 필요한 메서드가 없는데 내가 만들 수도 없을때
- 3rd party library 사용 시
- Date 클래스에서 nextDay 계산하기 필요한 경우

> 일주일 내 영수증 가져오면 환불될 때. 일주일 지났나 메서드

### Introduce Local Extension
- introduce foreign method를 위한 명확한 방법
- 서브클래스 또는 래퍼클래스를 Local Extension 이라고 부름
- 서브클래스
```
class MfDateSub extends Date {
 // 생성자 정의
  public MfDateSub (String dateString) {
    super(dateString);
  }
// 변환 생성자 정의
  public MfDateSub (Date arg) {
    super(arg.getTime())
  }
//필요한 외래 메소드 정의
  Date nextDay() {
    return new Date (getYear(), getMonth(), getDate() + 1);
  }
}
```
- wrapper 클래스
```
class MfDateWrap {
  private Date _original;
//생성자 정의
  public MfDateWrap(String dateString) {
    _original = new Date(dateString);
  }
//변환 생성자 정의
  public MfDateWrap(Date arg) {
    _original = arg;
  }
//기존 기능 위임 메소드
  public int getYear() {
    return _original.getYear();
  }
//기존 기능 위임 메소드
  public boolean equals (MfDateWrap arg) {
    return (toDate().equals(arg.toDate()));
  }
//필요한 외래 메소드 정의
  Date nextDay() {
    return new Date (getYear(), getMonth(), getDate() + 1);
  }
}
```

# Organizing Data
- 데이터를 좀 더 쉽게 다룰 수 있도록

### Replace Array with Object 
- 배열로 가지고 있는 원소를 객체로 전환

### Replace Magic Number with Symbolic Constant

### Encapsulate Field
- public 필드를 private으로 변경. getter/setter 추가

### Encapsulate Collection
```
## Order Class
public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
```

### Replace Type Code with Class
- 숫자형 분류 부호는 의미가 없는 단순한 숫자다
- 이러한 분류코드를 쓰는 가장 큰 이유는 DB에 저장할때임. 하지만 어떤값을 넣는지 검증할 수가 없음
- enum 클래스로 쓰면 IDE에서도 검증되고 명시적으로 표현할 수 있음

-> Beverage Size

### Replace Type Code with Subclass
- bulky switch문이 아니라 적절한 subclass만 가져다 사용. SRP에 맞음
- 클라이언트가 subclass 선택해서 사용
- Open-closed principle 새로 subclass 추가해도 다른 것들에 영향 전혀 없음

> Payment 종류를 enum으로 관리하다 subclass로 분리함


### Replace Type Code with State/Strategy
- 이미 기존에 hierarchy 구조라서 subclass 불가한 경우

# Simplifying Conditional Expressions
- 조건문 간결화

### Decompose Conditional
- 조건문 쪼개기
- if..else 조건문 메서드로 추출

### Replace Conditional with Polymorphism
> 적립률 Payment 마다 다르게

# Simplifying Method Calls

### Replace Parameter with Explicit Methods 

### Preserve Whole Object





# Reference
https://wikidocs.net/590
https://refactoring.guru/introduce-local-extension
https://refactoring.com/catalog/hideDelegate.html
