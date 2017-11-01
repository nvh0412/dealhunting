import React, { Component } from 'react';
import { Animated, View, Text, FlatList, TextInput, Image } from 'react-native';
import { connect } from 'react-redux';
import { getCustomer } from '../../actions/customer';
import Icon from 'react-native-vector-icons/Ionicons';
import Colors from '../../common/Colors';
import BillItem from '../bill/BillItem';
import { UserProfile, Spinner } from '../common';

class UserDetailScreen extends Component {
  constructor(props) {
    super(props);

    this.state = { isLoading: true };
  }

  componentDidMount() {
    this.props.getCustomer(this.props.token, this.props.id);
  }

  renderRow = ({item}) => {
    const { token } = this.props;

    return (
      <BillItem item={item} token={token} disableSwipe />
    )
  }

  componentWillReceiveProps(nextProps) {
    this.setState({ isLoading: false });
  }

  _keyExtractor = (item, index) => item.id;

  renderHeader = () => {
    const { customer } = this.props;

    return (
      <View style={{ flex: 1, marginBottom: 8 }}>
        <UserProfile
          name={customer.full_name}
          total={customer.total_paid}
          address={customer.ship_address}
          phone={customer.phone_number}
        />
      </View>
    )
  }

  render() {
    const { customer } = this.props;

    return (this.state.isLoading ? <Spinner /> :
      <View style={styles.container}>
        <FlatList
          style={styles.list}
          data={customer.bills}
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
  }
}

const mapStateToProps = (state) => {
  return {
    customer: state.customer.customer,
    token: state.auth.user.spree_api_key
  };
}

export default connect(mapStateToProps, { getCustomer })(UserDetailScreen);
