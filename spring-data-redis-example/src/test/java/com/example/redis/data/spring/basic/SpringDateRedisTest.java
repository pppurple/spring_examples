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
    private StringRedisTemplate redisTemplate;

    @Test
    public void basic() {
        redisTemplate.opsForValue().set("my_key", "my_val");
        System.out.println(redisTemplate.opsForValue().get("my_key"));
    }

    @Test
    public void redisTemplate() throws InterruptedException {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // delete()
        // delete()で指定したキーの値を削除。
        ops.set("my_key", "my_val");
        System.out.println(ops.get("my_key"));
        redisTemplate.delete("my_key");
        System.out.println(ops.get("my_key"));

        // hasKey()
        // hasKey()で指定したキーが存在する場合1、存在しない場合0
        Map<String, String> map = new HashMap<>();
        map.put("AAA", "111");
        map.put("BBB", "222");
        map.put("CCC", "333");
        ops.multiSet(map);
        System.out.println(redisTemplate.hasKey("AAA"));
        System.out.println(redisTemplate.hasKey("DDD"));

        // expire()
        // expire()で対象キーの存続時間(秒)を指定できる。
        // 存続時間を過るとキーは自動的に削除される。
        ops.set("my_key", "ABCDE");
        redisTemplate.expire("my_key", 5, TimeUnit.SECONDS);
        System.out.println(ops.get("my_key"));
        System.out.println("Wait for 6 sec...");
        Thread.sleep(6_000L);
        System.out.println(ops.get("my_key"));
    }

    @Test
    public void string() throws InterruptedException {
        redisTemplate.delete("my_key");
        redisTemplate.delete("new_key");

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // set(), get()
        // set()で指定したキーに値を設定。
        // get()で指定したキーの値をフェッチ。
        ops.set("my_key", "my_val");
        System.out.println(ops.get("my_key"));

        // setIfAbsent()
        // setIfAbsent()で存在しないキーの場合は設定される
        ops.setIfAbsent("new_key", "new_val");
        System.out.println(ops.get("new_key"));

        // increment()
        // increment()で第2引数で指定した数だけ値をインクリメント、
        // マイナス値を指定するとデクリメント
        ops.set("my_count", "100");
        ops.increment("my_count", 10L);
        System.out.println(ops.get("my_count"));

        ops.increment("my_count", -10L);
        System.out.println(ops.get("my_count"));

        // multiSet(), multiGet()
        // multiSet()でキーと値のMapを指定することで複数の値を一度にセットできる。
        // multiGet()でキーのコレクションを指定すると複数の値を一度にフェッチできる。
        Map<String, String> map = new HashMap<>();
        map.put("AAA", "111");
        map.put("BBB", "222");
        map.put("CCC", "333");
        ops.multiSet(map);
        System.out.println(ops.multiGet(Arrays.asList("AAA", "BBB", "CCC")));

        // set() with expire
        // set()に第3、4引数で存続時間を指定できる。
        // 存続時間を過るとキーは自動的に削除される。
        ops.set("my_key", "abcde", 5, TimeUnit.SECONDS);
        System.out.println(ops.get("my_key"));
        System.out.println("Wait for 6 sec...");
        Thread.sleep(6_000L);
        System.out.println(ops.get("my_key"));
    }

    @Test
    public void list() {
        redisTemplate.delete("my_list");

        ListOperations<String, String> ops = redisTemplate.opsForList();

        // rightPush()
        // rightPush()でListの末尾に追加
        ops.rightPush("my_list", "aaa");
        ops.rightPush("my_list", "bbb");
        ops.rightPush("my_list", "ccc");
        ops.rightPush("my_list", "ddd");
        System.out.println(ops.range("my_list", 0, -1));

        // rightPushAll()
        // rightPushAll()で複数の値を一度にrightPush可能
        redisTemplate.delete("my_list");
        ops.rightPushAll("my_list", "aaa", "bbb", "ccc", "ddd");
        System.out.println(ops.range("my_list", 0, -1));

        // 要素のコレクションを指定することでも一度にrightPush可能
        redisTemplate.delete("my_list");
        List<String> names = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        ops.rightPushAll("my_list", names);
        System.out.println(ops.range("my_list", 0, -1));

        // index()
        // 指定したインデックスの要素を取得
        System.out.println(ops.index("my_list", 0));
        System.out.println(ops.index("my_list", 2));

        // range()
        // 指定した範囲の要素を取得。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        System.out.println(ops.range("my_list", 0, 2));
        System.out.println(ops.range("my_list", 1, 3));
        System.out.println(ops.range("my_list", 0, -1));

        // leftPush()
        // リストの先頭に指定された要素を挿入。
        ops.leftPush("my_list", "aa");
        System.out.println(ops.range("my_list", 0, -1));

        // leftPop(), rightPop()
        // leftPop()でリストの最初の要素を削除して取得。
        // rightPop()でリストの末尾の要素を削除して取得。
        System.out.println(ops.range("my_list", 0, -1));
        System.out.println(ops.leftPop("my_list"));
        System.out.println(ops.range("my_list", 0, -1));
        System.out.println(ops.rightPop("my_list"));
        System.out.println(ops.range("my_list", 0, -1));

        // trim()
        // 指定した範囲を残すようにリストをトリムする。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        redisTemplate.delete("my_list");
        ops.rightPushAll("my_list", "aaa", "bbb", "ccc", "ddd");
        System.out.println(ops.range("my_list", 0, -1));
        ops.trim("my_list", 0, 1);
        System.out.println(ops.range("my_list", 0, -1));
    }

    @Test
    public void set() {
        redisTemplate.delete("my_set");

        SetOperations<String, String> ops = redisTemplate.opsForSet();

        // add(), members()
        // add()でセットに要素を追加。
        // members()でセットに含まれるすべての要素を取得。
        ops.add("my_set", "AAA");
        ops.add("my_set", "BBB");
        ops.add("my_set", "CCC");
        ops.add("my_set", "AAA");
        System.out.println(ops.members("my_set"));

        // 複数の要素を一度に追加可能
        redisTemplate.delete("my_set");
        ops.add("my_set", "AAA", "BBB", "CCC");
        System.out.println(ops.members("my_set"));

        // pop()
        // pop()でランダムに要素を削除して取得。
        System.out.println(ops.pop("my_set"));
        System.out.println(ops.members("my_set"));

        // isMember()
        // 指定した要素が格納されているかどうかを返す。
        redisTemplate.delete("my_set");
        ops.add("my_set", "AAA", "BBB", "CCC");
        Boolean existAAA = ops.isMember("my_set", "AAA");
        System.out.println(existAAA);
        Boolean existDDD = ops.isMember("my_set", "DDD");
        System.out.println(existDDD);

        // remove()
        // 指定した要素を削除.
        redisTemplate.delete("my_set");
        ops.add("my_set", "AAA", "BBB", "CCC");
        System.out.println(ops.members("my_set"));
        ops.remove("my_set", "BBB");
        System.out.println(ops.members("my_set"));
    }

    @Test
    public void hash() {
        redisTemplate.delete("my_hash");

        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();

        // put(), get()
        // put()で指定したフィールドに値を設定。
        // get()で指定したフィールドの値を取得。
        ops.put("my_hash", "aaa", "111");
        ops.put("my_hash", "bbb", "222");
        ops.put("my_hash", "ccc", "333");
        System.out.println(ops.get("my_hash", "aaa"));

        // putAll()
        // putAll()で指定したフィールドと値をまとめて設定。
        redisTemplate.delete("my_hash");
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "111");
        map.put("bbb", "222");
        map.put("ccc", "333");
        ops.putAll("my_hash", map);
        ops.entries("my_hash").forEach((key, value) -> System.out.println(key + ":" + value));

        // keys(), values(), multiGet(), entries()
        // keys()ですべてのキーを取得。
        // values()ですべての値を取得
        // multiGet()でキーのコレクションを指定すると、それらのキーに対する値を取得
        // entries()で指定したキーのすべてのフィールドと値を取得。
        System.out.println(ops.get("my_hash", "bbb"));
        System.out.println(ops.keys("my_hash"));
        System.out.println(ops.values("my_hash"));
        ops.multiGet("my_hash", ops.keys("my_hash")).forEach(System.out::println);
        ops.entries("my_hash").forEach((key, value) -> System.out.println(key + ":" + value));

        // delete()
        // delete()で指定したフィールドを削除。
        ops.entries("my_hash").forEach((key, value) -> System.out.println(key + ":" + value));
        ops.delete("my_hash", "ccc");
        ops.entries("my_hash").forEach((key, value) -> System.out.println(key + ":" + value));


        // increment()
        // increment()で第2引数で指定したフィールドの値を、第3指定指定した数だけインクリメント。
        System.out.println(ops.get("my_hash", "aaa"));
        ops.increment("my_hash", "aaa", 1);
        ops.increment("my_hash", "aaa", 1);
        System.out.println(ops.get("my_hash", "aaa"));
        ops.increment("my_hash", "aaa", 10);
        System.out.println(ops.get("my_hash", "aaa"));
    }

    @Test
    public void zset() {
        redisTemplate.delete("my_zset");

        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();

        // add()
        // add()でzsetに第3引数で指定したスコアで、第2引数のメンバを登録。
        ops.add("my_zset", "member1", 111);
        ops.add("my_zset", "member2", 222);
        ops.add("my_zset", "member3", 333);
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));

        // add()
        // TypedTupleのSetを渡すことでまとめて登録も可能
        redisTemplate.delete("my_zset");
        Set<TypedTuple<String>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>("member1", 111D));
        set.add(new DefaultTypedTuple<>("member2", 222D));
        set.add(new DefaultTypedTuple<>("member3", 333D));
        ops.add("my_zset", set);
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));

        // range(), rangeWithScores()
        // range()で指定した範囲のメンバを返す。
        // 第2引数は開始するインデックス、第3引数は終了するインデックス。
        // rangeWithScores()でスコアも同時に返す。
        System.out.println(ops.range("my_zset", 0, -1));
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));

        // rangeByScore(), rangeByScoreWithScores()
        // rangeByScore()で第2引数と第3引数の間のスコアを持つ要素を返す。
        // rangeByScoreWithScores()でスコアも同時に返す。
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

        // remove()
        // remove()で指定されたメンバを削除する。
        System.out.println(ops.range("my_zset", 0, -1));
        ops.remove("my_zset", "member1");
        System.out.println(ops.range("my_zset", 0, -1));

        // removeRangeByScore()
        // removeRangeByScore()で第2引数と第3引数の間のスコアを持つ要素を削除する。
        redisTemplate.delete("my_zset");
        ops.add("my_zset", set);
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));
        ops.removeRangeByScore("my_zset", 200, 400);
        ops.rangeWithScores("my_zset", 0, -1)
                .forEach(t -> System.out.println(t.getValue() + " : " + t.getScore()));
    }
}