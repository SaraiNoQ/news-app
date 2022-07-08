import React, {useState} from 'react';
import type {Node} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  Button,
  TouchableOpacity,
} from 'react-native';

import {NavigationContainer} from '@react-navigation/native';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import {
  Colors,
  DebugInstructions,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import IndexComponent from './pages/Home';
import NewsDetail from './pages/subPages/NewsDetail';
import MyCamera from './components/Camera';


const App: () => Node = () => {
  const Stack = createNativeStackNavigator();

  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="Index"
          component={IndexComponent}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="Detail"
          component={NewsDetail}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="Camera"
          component={MyCamera}
          options={{headerShown: false}}
        />

      </Stack.Navigator>
    </NavigationContainer>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
  buttonContainer: {
    marginTop: 32,
    marginBottom: 32,
    borderRadius: 5,
    backgroundColor: 'dodgerblue',
    border: '1px solid black',
    height: 40,
    lineHeight: 32,
    width: 120,
    marginLeft: 'auto',
    marginRight: 'auto',
  },
  buttonText: {
    fontSize: 16,
    lineHeight: 40,
    fontWeight: '400',
    textAlign: 'center',
  },
});

export default App;
