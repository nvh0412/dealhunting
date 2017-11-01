import React, { PureComponent } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image
} from 'react-native';
import moment from 'moment';
import numeral from 'numeral';
import Swipeout from 'react-native-swipeout';
import Colors from '../../common/Colors';

class BillItem extends PureComponent {
  render() {
    const { item, selectBill, removeBill, token, disableSwipe } = this.props;

    const swipeoutBtns = [
      {
        backgroundColor: Colors.redColor,
        underlayColor: Colors.redColor,
        onPress: () => removeBill(token, item.id),
        text: 'Xoá'
      },
      {
        backgroundColor: Colors.blueColor,
        underlayColor: Colors.blueColor,
        onPress: () => selectBill(item),
        text: 'Update'
      },
    ];

    return (
      <Swipeout autoClose right={swipeoutBtns} backgroundColor='white' disabled={disableSwipe}>
        <View key={item.id} style={styles.container}>
          <Image style={styles.icon} source={require('./img/bill_item_icon.png')}/>
          <View style={styles.content}>
            <View style={styles.title}>
              <Text style={{ flex: 1 }}>{item.full_name}</Text>
              <Text style={styles.price}>{numeral(item.price).format('0,0')}</Text>
            </View>
            <Text style={styles.address}>{item.ship_address}</Text>
          </View>
        </View>
      </Swipeout>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    height: 76,
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
    borderRadius: 24,
    borderWidth: 1,
    borderColor: Colors.grayBackground
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

export default BillItem;

