package com.project.commons.utils;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.druid.DruidDataSourceProvider;

import com.github.racc.tscg.TypesafeConfigModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class CommonUtils {
    private static Injector _injector;
    private static Config _conf;

    private static final String config_package = "com.graduation";

    public static Injector getInjector() {
        if (_injector == null) {
            synchronized (CommonUtils.class) {
                if (_injector == null) {
                    _injector = initGuice();
                }
            }
        }
        return _injector;
    }

    public static Config getConf() {
        if (_conf == null) {
            synchronized (CommonUtils.class) {
                if (_conf == null) {
                    File file = new File("conf/application.conf");
                    System.out.println(file.getAbsolutePath());
                    _conf = ConfigFactory.parseFile(file);
                }
            }
        }
        return _conf;
    }

    public static <T> T getInstance(Class<T> type) {

        return getInjector().getInstance(type);

    }

    protected static Injector initGuice() {
        Config conf = getConf();
        return Guice.createInjector(TypesafeConfigModule.fromConfigWithPackage(conf, "com.graduation"),
                new MyBatisModule() {
                    @Override
                    protected void initialize() {
                        bindDataSourceProviderType(DruidDataSourceProvider.class);
                        bindTransactionFactoryType(JdbcTransactionFactory.class);
                        addMapperClasses(conf.getString("application.mapper.package"));
                        bind(Config.class).toInstance(conf);
                        Names.bindProperties(binder(), buildDatabaseProperties(conf.getConfig("database")));
                    }
                }
        );
    }

    private static Properties buildDatabaseProperties(Config conf) {
        Properties myBatisProperties = new Properties();
        Set<Map.Entry<String, ConfigValue>> confSet = conf.entrySet();
        for (Map.Entry<String, ConfigValue> entry : confSet) {
            myBatisProperties.setProperty(entry.getKey(), conf.getString(entry.getKey()));
        }
        return myBatisProperties;
    }

    // vertx

    public static <T> T getInstanceConfigure(Class<T> type, String configurePath) {
        // getInstance 单例模式 直到需要 实例化该类 的时候 才将其实例化
        // 实例化 type 类
        return getInjectorConfigure(configurePath).getInstance(type);
    }

    public static Injector getInjectorConfigure(String configurePath) {
        if (_injector == null) {
            synchronized (CommonUtils.class) {
                if (_injector == null) {
                    _injector = initGuiceConfigure(configurePath);
                }
            }
        }
        return _injector;
    }

    protected static Injector initGuiceConfigure(String configurePath) {
        Config conf = getConf();
        return Guice.createInjector(TypesafeConfigModule.fromConfigWithPackage(conf, config_package),
                new MyBatisModule() {
                    @Override
                    protected void initialize() {
                        bindDataSourceProviderType(DruidDataSourceProvider.class);
                        bindTransactionFactoryType(JdbcTransactionFactory.class);
                        addMapperClasses(conf.getString(configurePath));
                        bind(Config.class).toInstance(conf);
                        Names.bindProperties(binder(), buildDatabaseProperties(conf.getConfig("database")));
                    }
                }
        );
    }


    public static Config getConf(String configurePath) {
        if (_conf == null) {
            synchronized (CommonUtils.class) {
                if (_conf == null) {
                    File file = new File(configurePath);
                    //System.out.println(file.getAbsolutePath());
                    _conf = ConfigFactory.parseFile(file);
                }
            }
        }
        return _conf;
    }
}