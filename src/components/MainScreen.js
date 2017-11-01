import React, { Component } from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  TouchableWithoutFeedback
} from 'react-native';
import { Actions } from 'react-native-router-flux';
import Icon from 'react-native-vector-icons/Ionicons';
import Colors from '../common/Colors';
import DashboardScreen from './dashboard/DashboardScreen';
import BillScreen from './bill/BillScreen';
import UserScreen from './user/UserScreen';

const DASHBOARD = 'dash';
const BILL = 'bill';
const USER = 'user';
const SETTING = 'setting';

class MainScreen extends Component {
  constructor(props) {
    super(props);

    this.state = {
      viewType: DASHBOARD
    };

    this.updateView = this.updateView.bind(this);
  }

  updateView(viewType) {
    this.setState({ viewType });
  }

  menuActive(type) {
    return type === this.state.viewType ? [styles.menuItem, styles.menuFocused] : styles.menuItem;
  }

  iconActive(type) {
    return type === this.state.viewType ? [styles.icon, styles.iconActive] : styles.icon;
  }

  renderView() {
    switch(this.state.viewType) {
      case BILL:
        return <BillScreen />;
      case USER:
        return <UserScreen />;
      default:
        return <DashboardScreen />;
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.menu}>
          <View>
            <Image source={require('./img/logo.jpg')}/>
          </View>
          <View style={{ justifyContent: 'center', flex: 1 }}>
            <TouchableWithoutFeedback onPress={() => this.updateView(DASHBOARD)}>
              <View style={this.menuActive(DASHBOARD)}>
                <Icon name="ios-pie-outline" style={this.iconActive(DASHBOARD)} />
              </View>
            </TouchableWithoutFeedback>
            <TouchableWithoutFeedback onPress={() => this.updateView(BILL)}>
              <View style={this.menuActive(BILL)}>
                <Icon name="ios-paper" style={this.iconActive(BILL)} />
              </View>
            </TouchableWithoutFeedback>
            <TouchableWithoutFeedback onPress={() => this.updateView(USER)}>
              <View style={this.menuActive(USER)}>
                <Icon name="ios-people" style={this.iconActive(USER)} />
              </View>
            </TouchableWithoutFeedback>
          </View>
          <TouchableWithoutFeedback onPress={() => Actions.setting()}>
            <View style={[this.menuActive(SETTING), styles.settingIcon]}>
              <Icon name="ios-settings" style={this.iconActive(SETTING)} />
            </View>
          </TouchableWithoutFeedback>
        </View>
        <View style={styles.view}>
          {this.renderView()}
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'row',
    backgroundColor: Colors.grayBackground
  },
  menu: {
    width: 36
  },
  view: {
    flex: 1,
    borderColor: '#ddd',
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 3},
    elevation: 3,
    shadowOpacity: 0.3
  },
  icon: {
    fontSize: 20,
    height: 22,
    color: Colors.grayButton
  },
  iconActive: {
    color: Colors.orangeColor
  },
  settingIcon: {
    marginTop: 'auto'
  },
  menuItem: {
    alignItems: 'center',
    paddingTop: 5,
    paddingBottom: 5
  },
  menuFocused: {
    borderLeftWidth: 3,
    borderColor: '#E9802C',
    backgroundColor: Colors.darkGrayBackground,
  }
});

export default MainScreen;

