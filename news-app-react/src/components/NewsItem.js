import React, {useState, useEffect} from 'react';
import {
  TouchableOpacity,
  Dimensions,
  StyleSheet,
  Image,
  Text,
  View,
} from 'react-native';
import dayjs from 'dayjs';

const { width: screenWidth, height: screenHeight } = Dimensions.get('window');

const NewsItem = (props) => {
    const { title, author, created_date } = props;

    return (
        <View style={styles.container} >
            <View style={styles.box}>
                <Text style={styles.title}>{title}</Text>
            </View>
            <View style={styles.edit}>
                <Text style={styles.author}>{author || '管理员添加'}</Text>
                <Text style={{ marginTop: 1.9, marginLeft: 8}}>{created_date || dayjs(Date.now()).format('YYYY-MM-DD')}</Text>
            </View>
        </View>
    )
};

const styles = StyleSheet.create({
    container: {
        backgroundColor: 'rgb(248, 248, 248)',
        borderBottomColor: 'rgb(230, 230, 230)',
        borderBottomWidth: 1,
    },
    edit: {
        marginTop: 6,
        flexDirection: 'row',
        justifyContent: 'flex-start',
        alignItems: 'center',
        paddingRight: 10,
        marginBottom: 15
    },
    title: {
        color: 'rgb(51, 51, 51)',
        fontSize: 21,
        fontWeight: 'bold',
        lineHeight: 30,
        marginLeft: 8,
        marginRight: 12,
        marginTop: 12,
    },
    author: {
        marginLeft: 8,
        marginRight: 12,
        marginTop: 3,
    }
});

export default NewsItem;