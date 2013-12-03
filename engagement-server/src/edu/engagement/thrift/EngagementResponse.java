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
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2013-12-2")
public class EngagementResponse implements org.apache.thrift.TBase<EngagementResponse, EngagementResponse._Fields>, java.io.Serializable, Cloneable, Comparable<EngagementResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EngagementResponse");

  private static final org.apache.thrift.protocol.TField GOOGLE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("googleId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField EVENT_INFORMATION_FIELD_DESC = new org.apache.thrift.protocol.TField("eventInformation", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new EngagementResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new EngagementResponseTupleSchemeFactory());
  }

  public String googleId; // optional
  public List<EventInfo> eventInformation; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GOOGLE_ID((short)1, "googleId"),
    EVENT_INFORMATION((short)2, "eventInformation");

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
        case 2: // EVENT_INFORMATION
          return EVENT_INFORMATION;
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
  private _Fields optionals[] = {_Fields.GOOGLE_ID,_Fields.EVENT_INFORMATION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GOOGLE_ID, new org.apache.thrift.meta_data.FieldMetaData("googleId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EVENT_INFORMATION, new org.apache.thrift.meta_data.FieldMetaData("eventInformation", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, EventInfo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EngagementResponse.class, metaDataMap);
  }

  public EngagementResponse() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EngagementResponse(EngagementResponse other) {
    if (other.isSetGoogleId()) {
      this.googleId = other.googleId;
    }
    if (other.isSetEventInformation()) {
      List<EventInfo> __this__eventInformation = new ArrayList<EventInfo>(other.eventInformation.size());
      for (EventInfo other_element : other.eventInformation) {
        __this__eventInformation.add(new EventInfo(other_element));
      }
      this.eventInformation = __this__eventInformation;
    }
  }

  public EngagementResponse deepCopy() {
    return new EngagementResponse(this);
  }

  @Override
  public void clear() {
    this.googleId = null;
    this.eventInformation = null;
  }

  public String getGoogleId() {
    return this.googleId;
  }

  public EngagementResponse setGoogleId(String googleId) {
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

  public int getEventInformationSize() {
    return (this.eventInformation == null) ? 0 : this.eventInformation.size();
  }

  public java.util.Iterator<EventInfo> getEventInformationIterator() {
    return (this.eventInformation == null) ? null : this.eventInformation.iterator();
  }

  public void addToEventInformation(EventInfo elem) {
    if (this.eventInformation == null) {
      this.eventInformation = new ArrayList<EventInfo>();
    }
    this.eventInformation.add(elem);
  }

  public List<EventInfo> getEventInformation() {
    return this.eventInformation;
  }

  public EngagementResponse setEventInformation(List<EventInfo> eventInformation) {
    this.eventInformation = eventInformation;
    return this;
  }

  public void unsetEventInformation() {
    this.eventInformation = null;
  }

  /** Returns true if field eventInformation is set (has been assigned a value) and false otherwise */
  public boolean isSetEventInformation() {
    return this.eventInformation != null;
  }

  public void setEventInformationIsSet(boolean value) {
    if (!value) {
      this.eventInformation = null;
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

    case EVENT_INFORMATION:
      if (value == null) {
        unsetEventInformation();
      } else {
        setEventInformation((List<EventInfo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GOOGLE_ID:
      return getGoogleId();

    case EVENT_INFORMATION:
      return getEventInformation();

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
    case EVENT_INFORMATION:
      return isSetEventInformation();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof EngagementResponse)
      return this.equals((EngagementResponse)that);
    return false;
  }

  public boolean equals(EngagementResponse that) {
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

    boolean this_present_eventInformation = true && this.isSetEventInformation();
    boolean that_present_eventInformation = true && that.isSetEventInformation();
    if (this_present_eventInformation || that_present_eventInformation) {
      if (!(this_present_eventInformation && that_present_eventInformation))
        return false;
      if (!this.eventInformation.equals(that.eventInformation))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(EngagementResponse other) {
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
    lastComparison = Boolean.valueOf(isSetEventInformation()).compareTo(other.isSetEventInformation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEventInformation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.eventInformation, other.eventInformation);
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
    StringBuilder sb = new StringBuilder("EngagementResponse(");
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
    if (isSetEventInformation()) {
      if (!first) sb.append(", ");
      sb.append("eventInformation:");
      if (this.eventInformation == null) {
        sb.append("null");
      } else {
        sb.append(this.eventInformation);
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

  private static class EngagementResponseStandardSchemeFactory implements SchemeFactory {
    public EngagementResponseStandardScheme getScheme() {
      return new EngagementResponseStandardScheme();
    }
  }

  private static class EngagementResponseStandardScheme extends StandardScheme<EngagementResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EngagementResponse struct) throws org.apache.thrift.TException {
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
          case 2: // EVENT_INFORMATION
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list40 = iprot.readListBegin();
                struct.eventInformation = new ArrayList<EventInfo>(_list40.size);
                for (int _i41 = 0; _i41 < _list40.size; ++_i41)
                {
                  EventInfo _elem42;
                  _elem42 = new EventInfo();
                  _elem42.read(iprot);
                  struct.eventInformation.add(_elem42);
                }
                iprot.readListEnd();
              }
              struct.setEventInformationIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EngagementResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.googleId != null) {
        if (struct.isSetGoogleId()) {
          oprot.writeFieldBegin(GOOGLE_ID_FIELD_DESC);
          oprot.writeString(struct.googleId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.eventInformation != null) {
        if (struct.isSetEventInformation()) {
          oprot.writeFieldBegin(EVENT_INFORMATION_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.eventInformation.size()));
            for (EventInfo _iter43 : struct.eventInformation)
            {
              _iter43.write(oprot);
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

  private static class EngagementResponseTupleSchemeFactory implements SchemeFactory {
    public EngagementResponseTupleScheme getScheme() {
      return new EngagementResponseTupleScheme();
    }
  }

  private static class EngagementResponseTupleScheme extends TupleScheme<EngagementResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EngagementResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGoogleId()) {
        optionals.set(0);
      }
      if (struct.isSetEventInformation()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetGoogleId()) {
        oprot.writeString(struct.googleId);
      }
      if (struct.isSetEventInformation()) {
        {
          oprot.writeI32(struct.eventInformation.size());
          for (EventInfo _iter44 : struct.eventInformation)
          {
            _iter44.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EngagementResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.googleId = iprot.readString();
        struct.setGoogleIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list45 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.eventInformation = new ArrayList<EventInfo>(_list45.size);
          for (int _i46 = 0; _i46 < _list45.size; ++_i46)
          {
            EventInfo _elem47;
            _elem47 = new EventInfo();
            _elem47.read(iprot);
            struct.eventInformation.add(_elem47);
          }
        }
        struct.setEventInformationIsSet(true);
      }
    }
  }

}

