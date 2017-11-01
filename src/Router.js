import React from 'react';
import { Stack, Scene, Router, Lightbox, Modal, Actions, Overlay } from 'react-native-router-flux';
import LoginScreen from './components/login/LoginScreen';
import MainScreen from './components/MainScreen';
import BillForm from './components/bill/BillForm';
import UserForm from './components/user/UserForm';
import UserDetailScreen from './components/user/UserDetailScreen';
import SettingScreen from './components/setting/SettingScreen';

const QBRouter = () => {
  return (
    <Router>
      <Stack hideNavBar key="root">
        <Stack key="main" modal>
          <Scene key="home" hideNavBar component={MainScreen} />
          <Scene key="billForm" component={BillForm} title="Hoá đơn" />
          <Scene key="customerForm" component={UserForm} title="Khách hàng" />
          <Scene key="setting" component={SettingScreen} title="Setting"/>
          <Scene key="customerDetail" component={UserDetailScreen} title="Thông Tin" />
        </Stack>
      </Stack>
    </Router>
  );
};

export default QBRouter;

