import * as React from 'react';
import {
  View,
  useWindowDimensions,
  StyleSheet,
  Text,
  ScrollView,
  Animated,
  Dimensions,
  TouchableHighlight,
  TouchableOpacity,
} from 'react-native';
import { TabView, SceneMap, TabBar } from 'react-native-tab-view';
import api from '../utils/api';

import ajax from '../utils/fetch';
import NewsItem from '../components/NewsItem';
import { Skeleton, VStack, NativeBaseProvider, HStack } from 'native-base';

const { width: screenWidth, height: screenHeight } = Dimensions.get('window');

const Throb = () => {
  return (
    <NativeBaseProvider>
     <VStack w="100%" h={screenHeight * 0.15} borderWidth="1" space={8} overflow="hidden" rounded="md" _dark={{
      borderColor: "coolGray.500"
    }} _light={{
      borderColor: "coolGray.200"
    }}>
        <Skeleton.Text px="4" mt="4" />
        <HStack space="2" alignItems="center"><Skeleton px="4" h="4" rounded="full" mt={-5} mr={screenWidth * 0.6} flex="1" startColor="indigo.300" /></HStack>
      </VStack>
    </NativeBaseProvider>
  )
};


const styles = StyleSheet.create({
});

export default function TabViewExample(props) {
  const { navigation } = props;
  const layout = useWindowDimensions();

  const [index, setIndex] = React.useState(0);
  const [routes, setRoutes] = React.useState([
    { key: '科技', title: '科技' },
    { key: '财经', title: '财经' },
    { key: '娱乐', title: '娱乐' },
    { key: '体育', title: '体育' },
    { key: '军事', title: '军事' },
    { key: '政务', title: '政务' },
    { key: '国际', title: '国际' },
    { key: '文化', title: '文化' },
    { key: '国内', title: '国内' },
  ]);
  const FirstRoute = (props) => {
    const tmap = {
      科技: 'Tech',
      国际: 'World',
      娱乐: 'Ent',
      体育: 'Sports',
      军事: 'Mil',
      财经: 'Fina',
      政务: 'Gov',
      文化: 'Cul',
      国内: 'Inner',
    }

    React.useEffect(() => {
      if (props.route.key === routes[index].key) {
        const apiParam = tmap[props.route.key]
        ajax({
          url: `${api()}/backend/show${apiParam}News`,
          success: data => {
            const resData = data.data;
            setData(resData);
          },
          error: err => {
            alert('您的网络异常！')
            console.log(err);
          }
        })
      }
    }, [index])

    const [data, setData] = React.useState([]);
    if (data.length > 0) {
      return (
        <ScrollView style={{ flex: 1, backgroundColor: 'white' }}>
          {data.map((item) => {
            return (
              <TouchableHighlight
                key={item.title}
                onPress={() => {
                  navigation.navigate('Detail', {
                    title: item.title,
                  })
                }}
              >
                <NewsItem
                  title={item.title}
                />
              </TouchableHighlight>
            )
          })}
        </ScrollView>
      )
    } else {
      return (
        <View style={{ flex: 1, backgroundColor: 'white' }}>
          <Throb /><Throb /><Throb /><Throb /><Throb />
        </View>
      )
    }
  };
  const renderScene = SceneMap({
    科技: FirstRoute,
    财经: FirstRoute,
    娱乐: FirstRoute,
    体育: FirstRoute,
    军事: FirstRoute,
    政务: FirstRoute,
    国内: FirstRoute,
    国际: FirstRoute,
    文化: FirstRoute,
  });

  React.useEffect(() => {
    ajax({
      url: api() + '/backend/showNewsType',
      success: data => {
        const resData = data.data;
        const newTabArr = [];
        resData.forEach(item => {
          newTabArr.push({
            key: item.type,
            title: item.type,
          })
        });
        // const smap = {}
        // newTabArr.forEach(item => {
        //   smap[item.key] = FirstRoute;
        // });
        // renderScene = SceneMap(smap);
        // setRoutes(newTabArr);
      },
      error: err => {
      }
    });
  }, [])

  return (
    <TabView
      navigationState={{ index, routes }}
      renderScene={renderScene}
      onIndexChange={(i) => setIndex(i)}
      initialLayout={{ width: layout.width }}
      renderTabBar={props => <TabBar {...props}
        scrollEnabled={true}
        indicatorStyle={{ backgroundColor: '#d81e06', }}
        style={{ backgroundColor: 'white' }}
        labelStyle={{ color: 'black', height: 35 }}
        activeColor={'#d81e06'}
        tabStyle={{ width: 80, height: 35 }}
      />}
      />
  );
}