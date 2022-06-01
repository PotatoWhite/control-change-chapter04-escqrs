# Event Sourcing 및 CQRS 구현하기
- 기본적인 EventSourcing과 CQRS를 구현한다.

# 구성
- commands Package는 Command를 담당하고,
- queries Package는 Query를 담당한다.
- Application을 나누지 않은 이유는 귀찮아서... 욕하지 말자.

# Commands Package는 UserAggregate가 중심이다.
- commands.commands package에 있는 요청 명령에 따라 동작하여 동작 결과가 commands.events package를 통해 발생된다.
- Event들은 발생과 함께 EventStore에 저장되고 Projection 된다.

# Queries Package는 ProjectedUser를 중심이다.
- 외부의 조회 요청에 따라 단순히 조회한다.

# Projection 
- Event들이 Replay되어 Query 모델로 넘어가는 과정이다.
- 본 예제에서는 Commands에서 Projection 시켰지만, 
- 실제 Query 모델은 Domain 별로 또는 Web, M-Web 등의 Channel 별로 다를 수 있다.
- 결론적으로 필요한 Context 별로 자신만의 Query Model 을 구축하는 것이 좋아보인다.(Query Model에서 직접 Projection을 해야 함)

# 개선 해야할 것
- 앞서 언급했든 projection은 Query 모델에서 해야함
- Event Store에 version을 명시적으로 해야함
- Event Store에 EventType을 별도의 field로 추출해야함
- Snapshot이 있어야 함
