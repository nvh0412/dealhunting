import React, { Component } from 'react';
import { View, Text, Image, ScrollView, Animated } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';
import Colors from '../../common/Colors';
import numeral from 'numeral';

class UserProfile extends Component {
  state = {
    scaleAnim: new Animated.Value(0),
  }

  componentDidMount() {
    Animated.timing(
      this.state.scaleAnim,
      {
        toValue: 230,
        duration: 1000
      }
    ).start();
  }

  render() {
    const { name, avatar, total, address, phone } = this.props;

    return (
      <View>
        <View style={styles.profile}>
          <View style={styles.row}>
            <Image style={styles.avatar} source={{ uri: ( avatar || `https://robohash.org/${name}.png?set=set4`) }} />
            <View style={styles.profileInfo}>
              <Text style={styles.profileName}>{(name || '').trim()}</Text>
              <Text style={{marginTop: 4, fontSize: 12}}>Tổng thu nhập</Text>
              <Text style={styles.total}>{numeral(total).format('0,0')}</Text>
            </View>
          </View>
          <View style={{ marginTop: 24 }}>
            <View style={styles.backgroundProgress}></View>
            <Animated.View style={[styles.progressBar, { width: this.state.scaleAnim }]}></Animated.View>
          </View>
          <View style={[styles.row, {marginTop: 8}]}>
            <Text style={styles.smallText}>0</Text>
            <Text style={[styles.smallText, {textAlign: 'center'}]}></Text>
            <Text style={[styles.smallText, {textAlign: 'right'}]}>10 tỷ</Text>
          </View>
        </View>
        {phone && <View style={[styles.addressContainer, { borderBottomWidth: 0 }]}>
          <Icon name="ios-pin-outline" style={styles.smallIcon} />
          <Text>{phone}</Text>
        </View>}
        <View style={styles.addressContainer}>
          <Icon name="ios-pin-outline" style={styles.smallIcon} />
          <Text>{address}</Text>
        </View>
      </View>
    );
  }
}

const styles = {
  profile: {
    width: '100%',
    backgroundColor: 'white',
    paddingLeft: 16,
    paddingRight: 16,
    paddingTop: 16,
    paddingBottom: 16
  },
  profileInfo: {
    marginLeft: 16
  },
  avatar: {
    width: 72,
    height: 72,
    borderRadius: 36,
    borderWidth: 1,
    borderColor: Colors.grayBorder
  },
  row: {
    flexDirection: 'row'
  },
  profileName: {
    color: Colors.orangeColor,
    fontSize: 18,
    fontWeight: 'bold',
  },
  total: {
    color: Colors.orangeColor,
    marginTop: 8,
    fontSize: 24,
    fontWeight: 'bold',
  },
  smallText: {
    fontSize: 12,
    flex: 3
  },
  backgroundProgress: {
    width: '100%',
    borderBottomWidth: 3,
    borderColor: Colors.lightOrangeColor
  },
  progressBar: {
    borderBottomWidth: 3,
    borderColor: Colors.orangeColor,
    position: 'absolute',
    top: 0,
    left: 0
  },
  addressContainer: {
    paddingLeft: 16,
    flexDirection: 'row',
    borderTopWidth: 1,
    borderColor: Colors.grayBorder,
    height: 50,
    backgroundColor: 'white',
    borderBottomWidth: 1,
    borderBottomColor: Colors.grayBorder,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    elevation: 2,
    shadowOpacity: 0.2,
    alignItems: 'center'
  },
  smallIcon: {
    fontSize: 20,
    height: 20,
    color: Colors.orangeColor,
    marginRight: 10,
  }
}

export default UserProfile;
