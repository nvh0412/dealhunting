import React, { Component } from 'react';
import {
  ScrollView,
  View,
  TouchableHighlight,
  Text,
  Button,
  PermissionsAndroid
} from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import moment from 'moment';
import t from 'tcomb-form-native';
import { connect } from 'react-redux';

import { Card, Spinner } from '../common';
import Colors from '../../common/Colors';
import { createBill, updateBill } from '../../actions/bill';

const Form = t.form.Form;
const Bill = t.struct({
  full_name: t.String,
  phone_number: t.String,
  ship_address: t.String,
  purchase_detail: t.String,
  price: t.Number,
  ship_at: t.Date,
  note: t.maybe(t.String)
});
const options = {
  fields: {
    full_name: {
      label: 'Họ Tên',
      autoCorrect: false
    },
    phone_number: {
      label: 'SĐT',
      autoCorrect: false
    },
    ship_address: {
      label: 'Địa chỉ giao hàng',
      autoCorrect: false
    },
    purchase_detail: {
      label: 'Đặt mua',
      autoCorrect: false
    },
    price: {
      label: 'Tổng'
    },
    ship_at: {
      label: 'Thời gian giao hàng',
      mode:'date',
      config: {
        format: (date) => moment(date).format("DD/MM/YYYY")
      }
    },
    note: {
      label: 'Ghi chú',
      autoCorrect: false
    }
  }
};

class BillForm extends Component {
  state = {
    isLoading: false,
    bill: this.props.bill
  }

  onPress = () => {
    const bill = this.refs.form.getValue();
    if (bill) {
      this.props.formType === 'NEW' ?
        this.props.createBill(this.props.authToken, bill) :
        this.props.updateBill(this.props.authToken, { ...bill, id: this.props.bill.id })
    }

    this.setState({ isLoading : true });
  };

  contactHandler = () => {
    this.requestCameraPermission();
  }

  requestCameraPermission = () => {
    PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.READ_CONTACTS,
      {
        'title': 'Quyền truy cập danh bạ',
        'message': 'ứng dụng sẽ lấy thông tin để điền vào hoá đơn'
      }
    ).then((granted) => {
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      }
    });
  }

  render() {
    let { bill } = this.state;
    bill.ship_at = moment(bill.ship_at).toDate();

    return (
      <KeyboardAwareScrollView behavior="position">
        <ScrollView>
          <Card>
            <Button title="Sử dụng danh bạ" onPress={this.contactHandler}/>
            <View style={styles.container}>
              <Form
                ref="form"
                type={Bill}
                options={options}
                value={bill}
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
  bill: state.bill.selectedBill,
  formType: state.bill.formType,
  authToken: state.auth.user.spree_api_key
});

export default connect(mapStateToProps, { createBill, updateBill })(BillForm);

