# java-movie-precourse

## 기능 요구 사항

- 하나의 영화는 주어진 상영 가능 기간에 하루에도 여러 차례 상영될 수 있다.
- 상영 시간은 영화의 상영 길이와 상영관 운영 시간에 따라 정해진다.
- 여러 영화를 한 번에 예매할 수도 있다. 이때 서로 시간이 겹치는 상영은 함께 예매할 수 없다.
- 사용자는 영화와 상영 시간을 고른 뒤 원하는 좌석을 선택할 수 있다.
- 각 좌석은 행(알파벳)과 열(숫자)로 표시된다. 예를 들어 A1, C3, E4와 같이 나타낸다.
- 좌석은 등급에 따라 가격이 다르다.
  - S석은 18,000원
  - A석은 15,000원
  - B석은 12,000원
- 이미 예약된 좌석은 다시 선택할 수 없다.
- 특정한 조건을 만족하는 예매자는 추가 혜택을 받을 수 있다.
  - 무비데이 조건: 매월 10일, 20일, 30일에 상영되는 영화는 10% 할인된다.
  - 시간 조건: 오전 11시 이전 또는 오후 8시 이후에 시작하는 상영은 2,000원이 할인된다.
  - 두 조건이 동시에 적용될 수 있으며, 무비데이 할인(비율)이 먼저 적용되고, 이어서 시간 조건 할인(정액)이 적용된다.
- 사용자는 보유한 포인트를 사용할 수 있다. 포인트는 예매 금액에서 직접 차감된다.
- 포인트를 적용한 뒤 결제 수단에 따른 추가 할인이 적용된다.
  - 신용카드 결제는 5% 할인
  - 현금 결제는 2% 할인


## 구현 기능 목록

### 🎬 0. 영화 (Movie)
영화 제목 및 상영 길이

### 🎬 1. 영화관 (MovieTheater)
스크린 소유

### 🏛️ 2. 스크린 (Place)
스크린 좌석 및 좌석 등급, 스크린 운영 시간

### 📅 3. 상영 (Screening)
영화 + 스크린 + 시작 시간

### 💰 4. 할인 정책 (DiscountPolicy)
무비데이 조건, 시간 조건

### 🎟️ 5. 예매 (Reservation)
사용자 + 상영 + 사용자가 선택한 좌석 + 할인 + 사용자 포인트 + 결제


## 구현 전략

### 핵심 도메인 정의 및 테스트
feat(domain): 영화(Movie) 클래스 정의 (제목, 상영 길이)

test(domain): Movie 클래스 생성 및 상영 길이 반환 테스트

feat(domain): 좌석 등급(SeatGrade) Enum 정의 (S/A/B 등급 및 가격)

test(domain): SeatGrade(등급) Enum 가격 반환 로직 테스트

feat(domain): 좌석(Seat) 클래스 정의 (위치, 등급, equals/hashCode)

test(domain): Seat 클래스 생성, 가격조회, equals/hashCode 로직 테스트

feat(domain): 스크린(Place) 클래스 정의 (운영 시간, List<Seat> 템플릿)

test(domain): Place 클래스 생성 및 좌석 목록 반환 테스트

feat(domain): 영화관(MovieTheater) 클래스 정의 (List<Place> 소유/관리)

test(domain): MovieTheater 클래스 스크린(Place) 탐색 로직 테스트

feat(domain): 상영(Screening) 클래스 정의 (Movie + Place + Time 조합)

test(domain): Screening 클래스 생성 및 할인 조건(시간/날짜) 판단 로직 테스트

feat(domain): 사용자(User) 및 결제수단(PaymentMethod) Enum 정의

feat(domain): 예매(Reservation) 클래스 (최종 결과 저장을 위한 뼈대)

test(domain): User, PaymentMethod, Reservation 등 기타 도메인 생성 테스트


### 가격 계산 로직 정의 및 테스트

### 예매 로직/검증 및 동시성 테스트

추후 추가하며 진행하겠습니다.