package com.example.redis.data.spring.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRedisConfig.class)
public class SpringDateRedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void basic() {
        stringRedisTemplate.opsForValue().set("my_key", "my_val");
        System.out.println(stringRedisTemplate.opsForValue().get("my_key"));

        stringRedisTemplate.delete("my_key");
        System.out.println(stringRedisTemplate.opsForValue().get("my_key"));
    }

    @Test
    public void string() throws InterruptedException {
        stringRedisTemplate.delete("my_key");
        stringRedisTemplate.delete("new_key");

        stringRedisTemplate.opsForValue().set("my_key", "my_val");
        System.out.println(stringRedisTemplate.opsForValue().get("my_key"));

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        // set(), get()
        // set()で指定したキーに値を設定。
        // get()で指定したキーの値をフェッチ。
        ops.set("my_key", "my_val");
        System.out.println(ops.get("my_key"));

        // del()
        // del()で指定したキーの値を削除。
        stringRedisTemplate.delete("my_key");
        System.out.println(ops.get("my_key"));

        // xx, nx
        // set()の第3引数にxx, nxを指定可能。
        // nx 存在しないキーの場合は設定される
        ops.setIfAbsent("new_key", "new_val");
        System.out.println(ops.get("new_key"));

        // xx 存在しているキーの場合は設定される
        ops.setIfAbsent("new_key", "new!!");
        System.out.println(ops.get("new_key"));

        // incr(), decr()
        // incr()で値をインクリメント、
        // incrBy()で指定した値だけインクリメント
        ops.set("my_count", "100");
        ops.increment("my_count", 10L);
        System.out.println(ops.get("my_count"));

        ops.increment("my_count", -10L);
        System.out.println(ops.get("my_count"));

        // mset(), mget()
        // mset()で複数の値を一度にセットできる。
        // mget()で複数の値を一度にフェッチできる。
        Map<String, String> map = new HashMap<>();
        map.put("AAA", "111");
        map.put("BBB", "222");
        map.put("CCC", "333");
        ops.multiSet(map);
        System.out.println(ops.multiGet(Arrays.asList("AAA", "BBB", "CCC")));

        // exists()
        // exists()で指定したキーが存在する場合1、存在しない場合0
        System.out.println(stringRedisTemplate.hasKey("AAA"));
        System.out.println(stringRedisTemplate.hasKey("DDD"));

        // expire()
        // expire()で対象キーの存続時間(秒)を指定できる。
        // 存続時間を過るとキーは自動的に削除される。
        ops.set("my_key", "abcde", 5, TimeUnit.SECONDS);
        System.out.println(ops.get("my_key"));
        System.out.println("Wait for 6 sec...");
        Thread.sleep(6_000L);
        System.out.println(ops.get("my_key"));

        ops.set("my_key", "ABCDE");
        stringRedisTemplate.expire("my_key", 5, TimeUnit.SECONDS);
        System.out.println(ops.get("my_key"));
        System.out.println("Wait for 6 sec...");
        Thread.sleep(6_000L);
        System.out.println(ops.get("my_key"));
    }

    @Test
    public void list() {
        stringRedisTemplate.delete("my_list");

        ListOperations<String, String> ops = stringRedisTemplate.opsForList();

        // rpush()
        // rpush()でListの末尾に追加
        ops.rightPush("my_list", "aaa");
        ops.rightPush("my_list", "bbb");
        ops.rightPush("my_list", "ccc");
        ops.rightPush("my_list", "ddd");

        // 複数の値を一度にrpush可能
        stringRedisTemplate.delete("my_list");
        ops.rightPushAll("aaa", "bbb", "ccc", "ddd");
        System.out.println(ops.range("my_list", 0, -1));

        stringRedisTemplate.delete("my_list");
        List<String> names = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        ops.rightPushAll("my_list", names);
        System.out.println(ops.range("my_list", 0, -1));

        // lindex()
        // 指定したインデックスの要素を取得
        System.out.println(ops.index("my_list", 0));
        System.out.println(ops.index("my_list", 2));

        // lrange()
        // 指定した範囲の要素を取得。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        System.out.println(ops.range("my_list", 0, 2));
        System.out.println(ops.range("my_list", 1, 3));
        System.out.println(ops.range("my_list", 0, -1));

        // lpush()
        // リストの先頭に指定された要素を挿入。
        ops.leftPush("my_list", "aa");
        System.out.println(ops.range("my_list", 0, -1));

        // lpop(), rpop()
        // lpop()でリストの最初の要素を削除して取得。
        // rpop()でリストの末尾の要素を削除して取得。
        System.out.println(ops.leftPop("my_list"));
        System.out.println(ops.range("my_list", 0, -1));
        System.out.println(ops.rightPop("my_list"));
        System.out.println(ops.range("my_list", 0, -1));

        // ltrim()
        // 指定した範囲を残すようにリストをトリムする。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        stringRedisTemplate.delete("my_list");
        ops.rightPushAll("aaa", "bbb", "ccc", "ddd");
        System.out.println(ops.range("my_list", 0, -1));
        ops.trim("my_list", 0, 1);
        System.out.println(ops.range("my_list", 0, -1));
    }

    @Test
    public void set() {
        stringRedisTemplate.delete("my_set");

        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();

        // sadd(), smembers()
        // sadd()でセットに要素を追加。
        // smembers()でセットに含まれるすべての要素を取得。
        ops.add("my_set", "AAA");
        ops.add("my_set", "BBB");
        ops.add("my_set", "CCC");
        ops.add("my_set", "AAA");
        System.out.println(ops.members("my_set"));

        // 複数の要素を一度に追加可能
        stringRedisTemplate.delete("my_set");
        ops.add("my_set", "AAA", "BBB", "CCC");
        System.out.println(ops.members("my_set"));

        // spop()
        // spop()でランダムに要素を削除して取得。
        ops.pop("my_set");
        System.out.println(ops.members("my_set"));

        // sismember()
        // 指定した要素が格納されているかどうか。
        // 格納されている場合は1、そうでない場合0
        stringRedisTemplate.delete("my_set");
        ops.add("my_set", "AAA", "BBB", "CCC");
        Boolean existAAA = ops.isMember("my_set", "AAA");
        System.out.println(existAAA);
        Boolean existDDD = ops.isMember("my_set", "DDD");
        System.out.println(existDDD);

        // srem()
        // 指定した要素を削除.
        stringRedisTemplate.delete("my_set");
        ops.add("my_set", "AAA", "BBB", "CCC");
        ops.remove("my_set", "BBB");
        System.out.println(ops.members("my_set"));
    }

    @Test
    public void hash() {
        stringRedisTemplate.delete("my_hash");

        HashOperations<String, Object, Object> ops = stringRedisTemplate.opsForHash();

        stringRedisTemplate.opsForHash().put("my_hash", "aaa", "111");
        stringRedisTemplate.opsForHash().put("my_hash", "bbb", "222");
        stringRedisTemplate.opsForHash().put("my_hash", "ccc", "333");
        System.out.println(stringRedisTemplate.opsForHash().get("my_hash", "bbb"));


        // hset(), hget(), hgetall(), hdel()
        // hset()で指定したフィールドに値を設定。
        // hget()で指定したフィールドの値を取得。
        // hgetAll()で指定したキーのすべてのフィールドと値を取得。
        // hdel()で指定したフィールドを削除。
        ops.put("my_hash", "aaa", "111");
        ops.put("my_hash", "bbb", "222");
        ops.put("my_hash", "ccc", "333");
        System.out.println(ops.get("my_hash", "aaa"));
        System.out.println(ops.get("my_hash", "bbb"));
        System.out.println(ops.keys("my_hash"));
        System.out.println(ops.values("my_hash"));
        ops.multiGet("my_hash", ops.keys("my_hash")).forEach(System.out::println);
        ops.entries("my_hash").forEach((key, value) -> System.out.println(key + ":" + value));
        ops.delete("my_hash", "ccc");
        ops.entries("my_hash").forEach((key, value) -> System.out.println(key + ":" + value));

        // hmset(), hmget()
        // hmset()で指定したフィールドと値をまとめて設定。
        // hmget()で指定したフィールドの値をまとめて取得。
        stringRedisTemplate.delete("my_hash");
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "111");
        map.put("bbb", "222");
        map.put("ccc", "333");
        ops.putAll("my_hash", map);
        ops.entries("my_hash").forEach((key, value) -> System.out.println(key + ":" + value));
        ops.multiGet("my_hash", ops.keys("my_hash")).forEach(System.out::println);

        // hincrBy()
        // 指定したフィールドの値をインクリメント。
        System.out.println(ops.get("my_hash", "aaa"));
        ops.increment("my_hash", "aaa", 1);
        ops.increment("my_hash", "aaa", 1);
        System.out.println(ops.get("my_hash", "aaa"));
        ops.increment("my_hash", "aaa", 10);
        System.out.println(ops.get("my_hash", "aaa"));
    }

    @Test
    public void zset() {
        stringRedisTemplate.delete("my_zset");

        ZSetOperations<String, String> ops = stringRedisTemplate.opsForZSet();

        // zadd()
        // zsetに第1引数で指定したスコアで、第2引数のメンバを登録。
        ops.add("my_zset", "member1", 111);
        ops.add("my_zset", "member2", 222);
        ops.add("my_zset", "member3", 333);

        // add
        // まとめて登録も可能
        stringRedisTemplate.delete("my_zset");
        Set<TypedTuple<String>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>("member1", 111D));
        set.add(new DefaultTypedTuple<>("member2", 222D));
        set.add(new DefaultTypedTuple<>("member3", 333D));
        ops.add("my_zset", set);

        // zrange(), zrangeWithScores()
        // zrange()で指定した範囲のメンバを返す。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        // zrangeWithScores()でスコアも同時に返す。
        System.out.println(ops.range("my_zset", 0, -1));
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));

        // zrangeByScore(), zrangeByScoreWithScores()
        // zrangeByScore()で第2引数と第3引数の間のスコアを持つ要素を返す。
        // zrangeByScoreWithScores()でスコアも同時に返す。
        System.out.println(ops.rangeByScore("my_zset", 0, 150));
        System.out.println(ops.rangeByScore("my_zset", 100, 300));
        ops.rangeByScoreWithScores("my_zset", 0, 150)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));
        ops.rangeByScoreWithScores("my_zset", 100, 230)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));

        // redis-cliだと、第2引数と第3引数には無限大(+inf/-inf)を指定することが可能だが、
        // jedisではDouble.MIN_VALUE/MAX_VALUEを使用する
        System.out.println(ops.rangeByScore("my_zset", Double.MIN_VALUE, 200));
        System.out.println(ops.rangeByScore("my_zset", 200, Double.MAX_VALUE));

        // zrem()
        // 指定されたメンバを削除する。
        System.out.println(ops.range("my_zset", 0, -1));
        ops.remove("my_zset", "member1");
        System.out.println(ops.range("my_zset", 0, -1));

        // zremrangeByScore()
        // 第1引数と第2引数の間のスコアを持つ要素を削除する。
        stringRedisTemplate.delete("my_zset");
        ops.add("my_zset", set);
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));
        ops.removeRangeByScore("my_zset", 200, 400);
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));
    }
}