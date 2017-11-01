import React from 'react';
import { Stack, Scene, Router, Lightbox, Modal, Actions, Overlay } from 'react-native-router-flux';
import LoginScreen from './components/login/LoginScreen';
import MainScreen from './components/MainScreen';
import SettingScreen from './components/setting/SettingScreen';

const QBRouter = () => {
  return (
    <Router>
      <Stack hideNavBar key="root">
        <Stack key="main" modal>
          <Scene key="home" hideNavBar component={MainScreen} />
          <Scene key="setting" component={SettingScreen} title="Setting"/>
        </Stack>
      </Stack>
    </Router>
  );
};

export default QBRouter;

