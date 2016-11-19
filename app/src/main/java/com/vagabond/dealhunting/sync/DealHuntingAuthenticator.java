package com.vagabond.dealhunting.sync;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/*
 * Copyright (C) 2016 SkyUnity (HoaNV)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class DealHuntingAuthenticator extends AbstractAccountAuthenticator {

  public DealHuntingAuthenticator(Context context) {
    super(context);
  }

  @Override
  public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String s, String s1, String[] strings, Bundle bundle) throws NetworkErrorException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
    return null;
  }

  @Override
  public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getAuthTokenLabel(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
    throw new UnsupportedOperationException();
  }
}
