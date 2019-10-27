package cache

import (
	"time"

	"github.com/patrickmn/go-cache"
)

const (
	DefaultExpiration = cache.DefaultExpiration
)

var (
	cacheInstance = cache.New(30*time.Minute, 1*time.Hour)
)

func Put(key string, val interface{}, expiration time.Duration) {
	cacheInstance.Set(key, val, expiration)
}

func PutStringMap(key string, val map[string]string, expiration time.Duration) {
	Put(key, val, expiration)
}

func PutString(key string, val string, expiration time.Duration) {
	Put(key, val, expiration)
}

func PutTime(key string, val time.Time, expiration time.Duration) {
	Put(key, val, expiration)
}

func PutInt64(key string, val int64, expiration time.Duration) {
	Put(key, val, expiration)
}

func Get(key string) (interface{}, bool) {
	data, ok := cacheInstance.Get(key)
	if !ok {
		return nil, false
	}

	return data, true
}

func GetStringMap(key string) (map[string]string, bool) {
	d, ok := Get(key)
	if !ok {
		return nil, false
	}
	if d, ok := d.(map[string]string); ok {
		return d, true
	}

	return nil, false
}

func GetInt64(key string) (int64, bool) {
	d, ok := Get(key)
	if !ok {
		return 0, false
	}
	if d, ok := d.(int64); ok {
		return d, true
	}

	return 0, false
}

func GetString(key string) (string, bool) {
	d, ok := Get(key)
	if !ok {
		return "", false
	}
	if d, ok := d.(string); ok {
		return d, true
	}

	return "", false
}

func GetTime(key string) (time.Time, bool) {
	d, ok := Get(key)
	if !ok {
		return time.Time{}, false
	}
	if d, ok := d.(time.Time); ok {
		return d, true
	}

	return time.Time{}, false
}

func Del(key string) {
	cacheInstance.Delete(key)
}

func Items() map[string]cache.Item {
	return cacheInstance.Items()
}
