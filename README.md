# java-attendance

## :sparkling_heart: 프로젝트 소개

웹 백엔드 7기 레벨1 출석 미션을 구현한 프로젝트입니다.

## :dart: 구현 기능 목록

## 출석 파일 입력

### 출석 파일 입력

- [x] 출석 파일 입력

    ```
    nickname,datetime
    쿠키,2024-12-13 10:08
    빙봉,2024-12-13 10:07
    빙티,2024-12-13 10:07
    이든,2024-12-13 10:07
    ```

## 메뉴

### 메뉴 입력받기

- [x] 메뉴 출력
- [x] 메뉴 선택하기 위한 커맨드를 입력

```
오늘은 12월 13일 금요일입니다. 기능을 선택해 주세요.
1. 출석 확인
2. 출석 수정
3. 크루별 출석 기록 확인
4. 제적 위험자 확인
Q. 종료
1
```

### 메뉴 반복 선택

- [ ] 선택된 메뉴에 대한 기능 수행을 완료하면 메뉴 입력 받기 기능을 다시 수행

```
오늘은 12월 13일 금요일입니다. 기능을 선택해 주세요.
1. 출석 확인
2. 출석 수정
3. 크루별 출석 기록 확인
4. 제적 위험자 확인
Q. 종료
1
```

## 출석 확인

### 입력

- [x] 닉네임

    ```
    닉네임을 입력해 주세요.
    이든
    ```

- [x] 등교시간

    ```
    등교 시간을 입력해 주세요.
    09:59
    ```

### 출석 저장

- [x] 닉네임과 등교 시간을 입력받아 출석 생성하여 저장
- [x] 교육 시간은 월요일은 13:00~18:00, 화요일~금요일은 10:00~18:00
    - 5분 초과는 지각
    - 30분 초과는 결석
    - 출석 기록이 없는 날은 결석
- [x] 캠퍼스 운영 시간은 08:00~23:00
- [x] [예외처리] 캠퍼스 운영시간이 아닌 경우
    ```
    [ERROR] 캠퍼스 운영시간이 아닙니다.
    ```

- [ ] [예외처리] 이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내

    ```
    [ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.
    ```

- [x] [예외처리] 주말과 공휴일인 경우 출석 저장 불가능

    ```
    [ERROR] 12월 14일 토요일은 등교일이 아닙니다.
    ```

- [x] [예외처리] 공휴일 중복 추가 불가능

    ```
    [ERROR] 이미 추가된 휴일입니다.
    ```

- [ ] [예외처리] 등록되지 않은 닉네임은 허용X

    ```
    [ERROR] 등록되지 않은 닉네임입니다.
    ```

### 출력

- [x] 출석 기록

    ```
    12월 13일 금요일 09:59 (출석)
    ```

## 출석 수정

### 입력

- [x] 닉네임

```
출석을 수정하려는 크루의 닉네임을 입력해 주세요.
빙티
```

- [x] 수정할 날짜

```
수정하려는 날짜(일)를 입력해 주세요.
3
```

- [x] 수정할 시간

```
언제로 변경하겠습니까?
09:58
```

### 출석 수정

- [x] 날짜에 해당하는 출석의 시간을 변경
- [x] 교육 시간은 월요일은 13:00-18:00, 화요일-금요일은 10:00-18:00 (주말 공휴일 출석x)
    - 5분 초과는 지각
    - 30분 초과는 결석
    - 출석 기록이 없는 날은 결석
- [x] 캠퍼스 운영 시간은 08:00~23:00
- [x] [예외처리] 캠퍼스 운영시간이 아닌 경우
    ```
    [ERROR] 캠퍼스 운영시간이 아닙니다.
    ```
- [x] [예외처리] 주말과 공휴일인 경우 출석 저장 불가능

    ```
    [ERROR] 12월 14일 토요일은 등교일이 아닙니다.
    ```

- [x] [예외처리] 공휴일 중복 추가 불가능

    ```
    [ERROR] 이미 추가된 휴일입니다.
    ```

- [ ] [예외처리] 등록되지 않은 닉네임은 허용X

    ```
    [ERROR] 등록되지 않은 닉네임입니다.
    ```

### 출력

- [x] 수정된 출석 기록 (변경 전과 후)

  ```
  12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료
  ```

## 크루별 출석 기록 확인

### 입력

- [x] 닉네임

    ```
    닉네임을 입력해 주세요.
    빙티
    ```

### 출석 조회

- [x] 닉네임에 해당하는 출석 조회
- [ ] [예외처리] 등록되지 않은 닉네임은 허용X

    ```
    [ERROR] 등록되지 않은 닉네임입니다.
    ```

### 출결 종합

- [x] 닉네임에 해당하는 출석/지각/결석 횟수 종합

### 출결 위험도 판단

- [x] 수강생이 출결 위험도 대상자인지 확인 (지각 3회는 결석 1회)
    - 경고 대상자: 결석 2회 이상
    - 면담 대상자: 결석 3회 이상
    - 제적 대상자: 결석 5회 초과

### 출력

- [x] 대상자 출석 기록

    ```
    이번 달 빙티의 출석 기록입니다.
    
    12월 02일 월요일 13:00 (출석)
    12월 03일 화요일 09:58 (출석)
    12월 04일 수요일 10:02 (출석)
    12월 05일 목요일 10:06 (지각)
    12월 06일 금요일 10:01 (출석)
    12월 09일 월요일 --:-- (결석)
    12월 10일 화요일 10:08 (지각)
    12월 11일 수요일 --:-- (결석)
    12월 12일 목요일 --:-- (결석)
    ```

- [x] 종합된 출결 상태

    ```
    출석: 4회
    지각: 2회
    결석: 3회
    ```

- [x] 면담 여부

    ```
    면담 대상자입니다.
    ```

## 제적 위험자 확인

### 제적 위험자 종합

- [x] 출석 기록을 바탕으로 제적 위험자를 종합할 수 있다.
- [x] 수강생이 출결 위험도 대상자인지 확인 (지각 3회는 결석 1회)
    - 경고 대상자: 결석 2회 이상
    - 면담 대상자: 결석 3회 이상
    - 제적 대상자: 결석 5회 초과
- [x] 정렬
    - 제적 위험자 (제적→면담→경고)
    - 정렬 순서는 지각을 결석으로 간주하여 내림차순
    - 출석 상태가 같으면 닉네임으로 오름차순

### 출력

- [x] 제적 위험자

```
제적 위험자 조회 결과
- 빙티: 결석 3회, 지각 2회 (면담)
- 이든: 결석 2회, 지각 4회 (면담)
- 쿠키: 결석 2회, 지각 2회 (경고)
- 빙봉: 결석 1회, 지각 5회 (경고)
```
