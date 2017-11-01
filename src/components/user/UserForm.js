import React, { Component } from 'react';
import {
  ScrollView,
  View,
  TouchableHighlight,
  Text
} from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import moment from 'moment';
import t from 'tcomb-form-native';
import { connect } from 'react-redux';

import { Card, Spinner } from '../common';
import Colors from '../../common/Colors';
import { updateCustomer } from '../../actions/customer';

const Form = t.form.Form;
const Customer = t.struct({
  full_name: t.String,
  phone_number: t.String,
  ship_address: t.String
});

const options = {
  fields: {
    full_name: {
      lable: 'Họ Tên',
      autoCorrect: false
    },
    phone_number: {
      lable: 'Số đt',
      autoCorrect: false
    },
    ship_address: {
      lable: 'Địa chỉ giao hàng',
      autoCorrect: false
    }
  }
}

class UserForm extends Component {
  state = {
    isLoading: false
  }

  onPress = () => {
    const customer = this.refs.form.getValue();
    if (customer) {
      this.props.updateCustomer(this.props.token, { ...customer, id: this.props.customer.id })
    }

    this.setState({ isLoading : true });
  };

  render() {
    const { customer } = this.props;

    return (
      <KeyboardAwareScrollView behavior="position">
        <ScrollView>
          <Card>
            <View style={styles.container}>
              <Form
                ref="form"
                type={Customer}
                options={options}
                value={customer}
              />
              { this.state.isLoading ? <Spinner size='small' /> :
                <TouchableHighlight style={styles.button} onPress={this.onPress} underlayColor='#99d9f4'>
                  <Text style={styles.buttonText}>Save</Text>
                </TouchableHighlight>
              }
            </View>
          </Card>
        </ScrollView>
      </KeyboardAwareScrollView>
    );
  }
}

const styles = {
  container: {
    padding: 10,
  },
  buttonText: {
    fontSize: 18,
    color: 'white',
    alignSelf: 'center'
  },
  button: {
    height: 36,
    backgroundColor: Colors.orangeColor,
    borderColor: Colors.orangeColor,
    borderWidth: 1,
    borderRadius: 8,
    marginBottom: 10,
    alignSelf: 'stretch',
    justifyContent: 'center'
  }
}

const mapStateToProps = (state) => ({
  customer: state.customer.selectedCustomer,
  token: state.auth.user.spree_api_key
})

export default connect(mapStateToProps, { updateCustomer })(UserForm);
