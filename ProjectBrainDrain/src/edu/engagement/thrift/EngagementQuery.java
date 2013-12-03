/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package edu.engagement.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngagementQuery implements org.apache.thrift.TBase<EngagementQuery, EngagementQuery._Fields>, java.io.Serializable, Cloneable, Comparable<EngagementQuery> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EngagementQuery");

  private static final org.apache.thrift.protocol.TField GOOGLE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("googleId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField EVENTS_FIELD_DESC = new org.apache.thrift.protocol.TField("events", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new EngagementQueryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new EngagementQueryTupleSchemeFactory());
  }

  public String googleId; // optional
  public List<Event> events; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GOOGLE_ID((short)1, "googleId"),
    EVENTS((short)2, "events");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // GOOGLE_ID
          return GOOGLE_ID;
        case 2: // EVENTS
          return EVENTS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private _Fields optionals[] = {_Fields.GOOGLE_ID,_Fields.EVENTS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GOOGLE_ID, new org.apache.thrift.meta_data.FieldMetaData("googleId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EVENTS, new org.apache.thrift.meta_data.FieldMetaData("events", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Event.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EngagementQuery.class, metaDataMap);
  }

  public EngagementQuery() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EngagementQuery(EngagementQuery other) {
    if (other.isSetGoogleId()) {
      this.googleId = other.googleId;
    }
    if (other.isSetEvents()) {
      List<Event> __this__events = new ArrayList<Event>(other.events.size());
      for (Event other_element : other.events) {
        __this__events.add(new Event(other_element));
      }
      this.events = __this__events;
    }
  }

  public EngagementQuery deepCopy() {
    return new EngagementQuery(this);
  }

  @Override
  public void clear() {
    this.googleId = null;
    this.events = null;
  }

  public String getGoogleId() {
    return this.googleId;
  }

  public EngagementQuery setGoogleId(String googleId) {
    this.googleId = googleId;
    return this;
  }

  public void unsetGoogleId() {
    this.googleId = null;
  }

  /** Returns true if field googleId is set (has been assigned a value) and false otherwise */
  public boolean isSetGoogleId() {
    return this.googleId != null;
  }

  public void setGoogleIdIsSet(boolean value) {
    if (!value) {
      this.googleId = null;
    }
  }

  public int getEventsSize() {
    return (this.events == null) ? 0 : this.events.size();
  }

  public java.util.Iterator<Event> getEventsIterator() {
    return (this.events == null) ? null : this.events.iterator();
  }

  public void addToEvents(Event elem) {
    if (this.events == null) {
      this.events = new ArrayList<Event>();
    }
    this.events.add(elem);
  }

  public List<Event> getEvents() {
    return this.events;
  }

  public EngagementQuery setEvents(List<Event> events) {
    this.events = events;
    return this;
  }

  public void unsetEvents() {
    this.events = null;
  }

  /** Returns true if field events is set (has been assigned a value) and false otherwise */
  public boolean isSetEvents() {
    return this.events != null;
  }

  public void setEventsIsSet(boolean value) {
    if (!value) {
      this.events = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GOOGLE_ID:
      if (value == null) {
        unsetGoogleId();
      } else {
        setGoogleId((String)value);
      }
      break;

    case EVENTS:
      if (value == null) {
        unsetEvents();
      } else {
        setEvents((List<Event>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GOOGLE_ID:
      return getGoogleId();

    case EVENTS:
      return getEvents();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GOOGLE_ID:
      return isSetGoogleId();
    case EVENTS:
      return isSetEvents();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof EngagementQuery)
      return this.equals((EngagementQuery)that);
    return false;
  }

  public boolean equals(EngagementQuery that) {
    if (that == null)
      return false;

    boolean this_present_googleId = true && this.isSetGoogleId();
    boolean that_present_googleId = true && that.isSetGoogleId();
    if (this_present_googleId || that_present_googleId) {
      if (!(this_present_googleId && that_present_googleId))
        return false;
      if (!this.googleId.equals(that.googleId))
        return false;
    }

    boolean this_present_events = true && this.isSetEvents();
    boolean that_present_events = true && that.isSetEvents();
    if (this_present_events || that_present_events) {
      if (!(this_present_events && that_present_events))
        return false;
      if (!this.events.equals(that.events))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(EngagementQuery other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGoogleId()).compareTo(other.isSetGoogleId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGoogleId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.googleId, other.googleId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEvents()).compareTo(other.isSetEvents());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEvents()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.events, other.events);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("EngagementQuery(");
    boolean first = true;

    if (isSetGoogleId()) {
      sb.append("googleId:");
      if (this.googleId == null) {
        sb.append("null");
      } else {
        sb.append(this.googleId);
      }
      first = false;
    }
    if (isSetEvents()) {
      if (!first) sb.append(", ");
      sb.append("events:");
      if (this.events == null) {
        sb.append("null");
      } else {
        sb.append(this.events);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class EngagementQueryStandardSchemeFactory implements SchemeFactory {
    public EngagementQueryStandardScheme getScheme() {
      return new EngagementQueryStandardScheme();
    }
  }

  private static class EngagementQueryStandardScheme extends StandardScheme<EngagementQuery> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EngagementQuery struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GOOGLE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.googleId = iprot.readString();
              struct.setGoogleIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // EVENTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list32 = iprot.readListBegin();
                struct.events = new ArrayList<Event>(_list32.size);
                for (int _i33 = 0; _i33 < _list32.size; ++_i33)
                {
                  Event _elem34;
                  _elem34 = new Event();
                  _elem34.read(iprot);
                  struct.events.add(_elem34);
                }
                iprot.readListEnd();
              }
              struct.setEventsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, EngagementQuery struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.googleId != null) {
        if (struct.isSetGoogleId()) {
          oprot.writeFieldBegin(GOOGLE_ID_FIELD_DESC);
          oprot.writeString(struct.googleId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.events != null) {
        if (struct.isSetEvents()) {
          oprot.writeFieldBegin(EVENTS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.events.size()));
            for (Event _iter35 : struct.events)
            {
              _iter35.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EngagementQueryTupleSchemeFactory implements SchemeFactory {
    public EngagementQueryTupleScheme getScheme() {
      return new EngagementQueryTupleScheme();
    }
  }

  private static class EngagementQueryTupleScheme extends TupleScheme<EngagementQuery> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EngagementQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGoogleId()) {
        optionals.set(0);
      }
      if (struct.isSetEvents()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetGoogleId()) {
        oprot.writeString(struct.googleId);
      }
      if (struct.isSetEvents()) {
        {
          oprot.writeI32(struct.events.size());
          for (Event _iter36 : struct.events)
          {
            _iter36.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EngagementQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.googleId = iprot.readString();
        struct.setGoogleIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.events = new ArrayList<Event>(_list37.size);
          for (int _i38 = 0; _i38 < _list37.size; ++_i38)
          {
            Event _elem39;
            _elem39 = new Event();
            _elem39.read(iprot);
            struct.events.add(_elem39);
          }
        }
        struct.setEventsIsSet(true);
      }
    }
  }

}

