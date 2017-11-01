import React, { Component } from 'react';
import { Animated, View, Text, FlatList, TextInput, Image } from 'react-native';
import { connect } from 'react-redux';
import { Actions } from 'react-native-router-flux';
import { getCustomers, selectCustomer } from '../../actions/customer';
import Icon from 'react-native-vector-icons/Ionicons';
import Colors from '../../common/Colors';
import UserItem from './UserItem';
import debounce from 'lodash/debounce';

class UserScreen extends Component {
  state = {
    keywordFilter: ''
  }

  constructor(props) {
    super(props);

    this.keywordChangeDelayed = debounce(this.keywordChange, 100);
  }

  componentDidMount() {
    this.props.getCustomers(this.props.token, this.state.keywordFilter);
  }

  _keyExtractor = (item, index) => item.id;

  _onPress = (id) => {
    Actions.customerDetail({ id});
  }

  renderRow = ({item}) => {
    const { selectCustomer, token } = this.props;
    return (
      <UserItem
        item={item}
        selectCustomer={selectCustomer}
        onPress={this._onPress}
      />
    );
  }

  keywordChange = (keyword) => {
    this.setState({ keywordFilter: keyword });
    this.props.getCustomers(this.props.token, this.state.keywordFilter);
  }

  renderHeader = () => {
    return (
      <View>
        <View style={styles.header}>
          <Image style={{ flex: 1 }} source={require('../img/user.jpg')} />
          <View style={styles.titleContainer}>
            <Text style={styles.title}>Khách Hàng</Text>
          </View>
        </View>
        <View style={styles.searchContainer}>
          <Icon name="md-search" style={styles.searchIcon} />
          <TextInput
            style={styles.search}
            autoCorrect={false}
            onChangeText={this.keywordChangeDelayed}
            value={this.state.keyword}
            placeholder='Tìm kiếm khách hàng'
          />
        </View>
      </View>
    )
  }

  render() {
    const { customers } = this.props;

    return (
      <View style={styles.container}>
        <FlatList
          style={styles.list}
          data={customers}
          keyExtractor={this._keyExtractor}
          renderItem={this.renderRow}
          ListHeaderComponent={this.renderHeader}
        />
      </View>
    );
  }
}

const styles = {
  container: {
    flex: 1
  },
  titleContainer: {
    position: 'absolute',
    bottom: 0,
    top: 0,
    left: 0,
    right: 0,
    backgroundColor: Colors.orangeColor,
    opacity: 0.8
  },
  titleNumber: {
    fontSize: 40,
    bottom: 50
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: 'white',
    position: 'absolute',
    bottom: 20,
    right: 16
  },
  list: {
    borderTopWidth: 1,
    borderTopColor: Colors.grayBorder,
    backgroundColor: Colors.grayBackground
  },
  header: {
    backgroundColor: Colors.orangeColor,
    height: 150,
    marginBottom: 8,
    borderBottomWidth: 1,
    borderBottomColor: Colors.grayBorder,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    elevation: 2,
    shadowOpacity: 0.2
  },
  searchContainer: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    height: 48,
    marginBottom: 8,
    backgroundColor: 'white',
    borderBottomWidth: 1,
    borderTopWidth: 1,
    borderColor: Colors.grayBorder,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    elevation: 2,
    shadowOpacity: 0.2
  },
  searchIcon: {
    marginLeft: 32,
    fontSize: 24,
  },
  search: {
    marginLeft: 20,
    flex: 1
  }
}

const mapStateToProps = (state) => {
  return {
    customers: state.customer.customers,
    token: state.auth.user.spree_api_key
  };
}

export default connect(mapStateToProps, { getCustomers, selectCustomer })(UserScreen);

