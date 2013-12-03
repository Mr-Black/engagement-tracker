namespace java edu.engagement.thrift

typedef string Timestamp

struct EegPower {
  1: optional Timestamp millisecondTimeStamp;
  2: optional i32 alpha;
  3: optional i32 beta;
  4: optional i32 theta;
}

struct EegAttention {
  1: optional Timestamp millisecondTimeStamp;
  2: optional i32 attention;
}

struct EegRaw {
  1: optional Timestamp millisecondTimeStamp;
  2: optional i32 raw;
}

struct HeartRate {
  1: optional Timestamp millsecondTimeStamp;
  2: optional i32 bpm;
}

struct EngagementInformation {
  1: optional string googleId;
  2: optional list<EegPower> eegPowerMessages;
  3: optional list<EegRaw> eegRawMessages;
  4: optional list<EegAttention> eegAttentionMessages;
  5: optional list<HeartRate> heartRateMessages;
}

struct Event {
  1: optional string name;
  2: optional string start;
  3: optional string finish;
}

struct EngagementQuery {
  1: optional string googleId;
  2: optional list<Event> events;
}

struct EventInfo {
  1: optional string name;
  2: optional double heartRateStandardDeviation;
  3: optional double averageEngagement;
}

struct EngagementResponse {
	1: optional string googleId;
	2: optional list<EventInfo> eventInformation;
}

exception EngagementServiceUnavailable {
  1: string message;
}

service EngagementService {
  
  void syncEngagementInformation(1:EngagementInformation info) throws 
    (1:EngagementServiceUnavailable unavailable);
  
  EngagementResponse getEngagmentInformation(1:EngagementQuery query) 
  	throws (1:EngagementServiceUnavailable unavailable);

}
