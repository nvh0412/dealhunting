package com.vagabond.dealhunting.ui;/*
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

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vagabond.dealhunting.R;

public class PermissionUtils {
  public static void requestPermission(AppCompatActivity activity, int requestId,
                                       String permission, boolean finishActivity) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
      // Display a dialog with rationale.
      PermissionUtils.RationaleDialog.newInstance(requestId, finishActivity)
          .show(activity.getSupportFragmentManager(), "dialog");
    } else {
      // Location permission has not been granted yet, request it.
      ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);

    }
  }

  public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults, String permission) {
    for (int i = 0; i < grantPermissions.length; i++) {
      if (permission.equals(grantPermissions[i])) {
        return grantResults[i] == PackageManager.PERMISSION_GRANTED;
      }
    }
    return false;
  }

  public static class PermissionDeniedDialog extends DialogFragment {

    private static final String ARGUMENT_FINISH_ACTIVITY = "finish";

    private boolean mFinishActivity = false;

    public static PermissionDeniedDialog newInstance(boolean finishActivity) {
      Bundle arguments = new Bundle();
      arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);

      PermissionDeniedDialog dialog = new PermissionDeniedDialog();
      dialog.setArguments(arguments);
      return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      mFinishActivity = getArguments().getBoolean(ARGUMENT_FINISH_ACTIVITY);

      return new AlertDialog.Builder(getActivity())
          .setMessage(R.string.location_permission_denied)
          .setPositiveButton(android.R.string.ok, null)
          .create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
      super.onDismiss(dialog);
      if (mFinishActivity) {
        Toast.makeText(getActivity(), R.string.permission_required_toast,
            Toast.LENGTH_SHORT).show();
        getActivity().finish();
      }
    }
  }

  public static class RationaleDialog extends DialogFragment {

    private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

    private static final String ARGUMENT_FINISH_ACTIVITY = "finish";

    private boolean mFinishActivity = false;

    public static RationaleDialog newInstance(int requestCode, boolean finishActivity) {
      Bundle arguments = new Bundle();
      arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
      arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);
      RationaleDialog dialog = new RationaleDialog();
      dialog.setArguments(arguments);
      return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      Bundle arguments = getArguments();
      final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
      mFinishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY);

      return new AlertDialog.Builder(getActivity())
          .setMessage(R.string.permission_rationale_location)
          .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              // After click on Ok, request the permission.
              ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
              // Do not finish the Activity while requesting permission.
              mFinishActivity = false;
            }
          })
          .setNegativeButton(android.R.string.cancel, null)
          .create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
      super.onDismiss(dialog);
      if (mFinishActivity) {
        Toast.makeText(getActivity(),
            R.string.permission_required_toast,
            Toast.LENGTH_SHORT)
            .show();
        getActivity().finish();
      }
    }
  }
}
