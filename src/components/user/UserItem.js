import React, { PureComponent } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableWithoutFeedback
} from 'react-native';
import moment from 'moment';
import numeral from 'numeral';
import Swipeout from 'react-native-swipeout';
import Colors from '../../common/Colors';

class UserItem extends PureComponent {
  _onPress = () => {
    const { item, onPress } = this.props;
    onPress(item.id);
  }

  render() {
    const { item, selectCustomer } = this.props;

    const swipeoutBtns = [
      {
        backgroundColor: Colors.blueColor,
        underlayColor: Colors.blueColor,
        onPress: () => selectCustomer(item),
        text: 'Update'
      },
    ];

    return (
      <Swipeout autoClose right={swipeoutBtns} backgroundColor='white'>
        <TouchableWithoutFeedback onPress={this._onPress}>
          <View key={item.id} style={styles.container}>
            <View style={styles.icon}>
              <Text style={styles.iconText}>{(item.full_name || "U").trim().charAt(0).toUpperCase()}</Text>
            </View>
            <View style={styles.content}>
              <View style={styles.title}>
                <Text style={{ flex: 1 }}>{(item.full_name || "U").trim()}</Text>
              </View>
              <Text style={styles.address}>{(item.ship_address || '').trim()}</Text>
              <Text style={styles.price}>{item.phone_number}</Text>
            </View>
          </View>
        </TouchableWithoutFeedback>
      </Swipeout>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    height: 84,
    flexDirection: 'row',
    paddingLeft: 16,
    paddingRight: 16,
    alignItems: 'center',
    borderTopWidth: 1,
    borderColor: Colors.grayBorder
  },
  icon: {
    width: 48,
    height: 48,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 24,
    borderWidth: 1,
    borderColor: Colors.grayBackground,
    backgroundColor: Colors.orangeColor
  },
  iconText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white'
  },
  content: {
    flex: 1,
    justifyContent: 'center',
    marginLeft: 8,
    height: 76,
  },
  title: {
    flexDirection: 'row'
  },
  address: {
    marginTop: 3,
    fontSize: 12,
    color: Colors.grayButton
  },
  price: {
    fontSize: 12,
    color: Colors.darkGrayBackGround
  }
});

export default UserItem;
