# refactoring-test
ICAgile 교육에서 Clean Code, Code smell, Refactoring with test 실습을 위한 sample repository 입니다.

# 1. Clean Code 시나리오
## 다형성
결제 수단별로 마일리지 적립률을 다르게 간다.
* 카드 결제 : 5%
* 현금 결제 : 10%
* 마일리지 결제 : 0% (마일리직 적립 호출할 필요 없다.)

## 주석
* 카드, 현금 마일리지 적립률을 주석으로 표현
* 결제 방식을 주석으로 대체
* JavaDoc 자동완성 이상한 주석사용

## 적절한 행 길이 유지
* 인덴트 무시, 공백 제거

## Data structure
* 마일리지 결제 수단별 적립포인트 조회
* 비포 : getRate(), getTotalCost() 가져와서 계산
* 애프터 : getMileagePoint() 생성

## 응집도
* **시나리오좀 쌓이면 그때 고민하자.**

## null 반환
OrderService.create()내부에서 beverage 조회 Optional에 null 리턴
```
for(OrderItemDTO orderItemDTO: orderDTO.getOrderItemDTOList()) {
    Beverage beverage = beverageRepository.findById(orderItemDTO.getBeverageId()).orElse(null);

    if(beverage == null) {
        continue;
    }
```

## Error Handling - Exception
* MileageApiService 내부에서 checked Exception throw하게끔 변경
* OrderService에서 try...catch 반복 체크
* transaction rollback 되지 않는 거 까지 ...

#  2. Code smell 시나리오
OrderService.create만 작정해서 200라인 이상으로 만든다.
* DTO -> map으로 만든다.
* 결제수단 DB에서 조회 
* 결제수단 별로 mileage 적립 중복 소스 if..else
* api 연계시 restTemplate -> feign client로 변경

## 긴 파라미터
* Object 없이 파라미터 6개 이상
* Data Clumps, Primitive Obsession 모두 커버

## Divergent Agent (수정의 산발)
* **책을 보고 샘플 소스확인 필요**

## 샷건 수술 (기능의 산재)
* 결제 수단에 따라 마일리지 적립률을 관리하는 constants 생성 시나리오
* 마일리지 적립 정책이 변경 시나리오

## Feature Envy, Middle Man
* before : OrderItemService에서 beverages 목록 조회 및 validation check 정의
* after : BeverageService로 이동

## Speculative Generality
* 1회성 util method 만들기

## Alternative Classes with Different Interfaces
* parameter에 boolean 값으로 코드 if..else -> 메서드 분리

## 공부할 것
- Parallel Inheritance Hierarchies
- Lazy Class
- Temporary Field
- Message Chains
- Inappropriate Intimacy : 순환참조인지??
- Refused Bequest