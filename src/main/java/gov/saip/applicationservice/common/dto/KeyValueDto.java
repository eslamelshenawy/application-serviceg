package gov.saip.applicationservice.common.dto;

public interface KeyValueDto<K, V> {
    public K getKey();

    public V getValue();
}
