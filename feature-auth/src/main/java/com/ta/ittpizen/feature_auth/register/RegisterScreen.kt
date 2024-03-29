package com.ta.ittpizen.feature_auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.ta.ittpizen.common.isValidEmail
import com.ta.ittpizen.domain.model.Resource
import com.ta.ittpizen.domain.model.auth.RegisterResult
import com.ta.ittpizen.feature_auth.di.authModule
import com.ta.ittpizen.ui.component.button.LargePrimaryButton
import com.ta.ittpizen.ui.component.text.TextBodyMedium
import com.ta.ittpizen.ui.component.text.TextBodySmall
import com.ta.ittpizen.ui.component.text.TextTitleSmall
import com.ta.ittpizen.ui.component.textbutton.TextButton
import com.ta.ittpizen.ui.component.textfield.DropDownTextFieldWithLabel
import com.ta.ittpizen.ui.component.textfield.OutlinedTextFieldWithLabel
import com.ta.ittpizen.ui.component.textfield.PasswordTextFieldWithLabel
import com.ta.ittpizen.ui.component.topappbar.DetailTopAppBar
import com.ta.ittpizen.ui.theme.ITTPizenTheme
import com.ta.ittpizen.ui.theme.SecondDarkGrey
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel(),
    navigateUp: () -> Unit = {},
    navigateToLoginScreen: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val dialogState = rememberUseCaseState()

    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    val genders = listOf("Male", "Female")
    val status = listOf("Student", "Alumni", "Lecturer", "Staff")

    val uiState by viewModel.registerUiState.collectAsStateWithLifecycle()
    val registerResult by viewModel.registerResult.collectAsStateWithLifecycle()

    val fullName = uiState.fullName
    val studentIdOrLectureId = uiState.studentIdOrLectureId
    val email = uiState.email
    val phone = uiState.phone
    val gender = uiState.gender
    val currentStatus = uiState.status
    val password = uiState.password
    val confirmPassword = uiState.confirmPassword

    val fullNameErrorMessage = uiState.fullNameErrorMessage
    val emailErrorMessage = uiState.emailErrorMessage
    val passwordErrorMessage = uiState.passwordErrorMessage
    val confirmPasswordErrorMessage = uiState.confirmPasswordErrorMessage

    val fullNameError by viewModel.fullNameError.collectAsStateWithLifecycle(initialValue = false)
    val emailError by viewModel.emailError.collectAsStateWithLifecycle(initialValue = false)
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle(initialValue = false)
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsStateWithLifecycle(initialValue = false)

    val buttonRegisterEnabled by viewModel.buttonRegisterEnabled.collectAsStateWithLifecycle(initialValue = false)
    val buttonRegisterLoading by viewModel.buttonRegisterLoading.collectAsStateWithLifecycle(initialValue = false)

    val registerSuccess by remember(key1 = registerResult) {
        mutableStateOf(registerResult is Resource.Success)
    }

    val updateFullName: (String) -> Unit = {
        val errorMessage = if (it.isNotEmpty()) "" else "Nama cannot be empty!"
        viewModel.updateFullName(it)
        viewModel.updateFullNameErrorMessage(errorMessage)
    }
    val updateEmail: (String) -> Unit = {
        val errorMessage = if (it.isValidEmail() || it.isEmpty()) "" else "Email format not valid!"
        viewModel.updateEmail(it)
        viewModel.updateEmailErrorMessage(errorMessage)
    }
    val updatePassword: (String) -> Unit = {
        val errorMessage = if (it.length >= 6 || it.isEmpty()) "" else "Password must be at least 6 characters!"
        viewModel.updatePassword(it)
        viewModel.updatePasswordErrorMessage(errorMessage)
    }
    val updateConfirmPassword: (String) -> Unit = {
        val errorMessage = if (it == password) "" else "Password not same!"
        viewModel.updateConfirmPassword(it)
        viewModel.updateConfirmPasswordErrorMessage(errorMessage)
    }

    val onPositiveButtonDialogClicked: () -> Unit = {
        if (registerSuccess) {
            navigateToLoginScreen()
        } else dialogState.finish()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateGender(genders[0])
        viewModel.updateStatus(status[0])
    }

    LaunchedEffect(key1 = registerResult) {
        if (registerResult is Resource.Error) {
            dialogTitle = "Register Failed!"
            dialogMessage = (registerResult as Resource.Error<RegisterResult>).message ?: ""
            dialogState.show()
        }
        if (registerResult is Resource.Success) {
            val message = "Registered $email successfully"
            dialogTitle = "Register Success!"
            dialogMessage = message
            dialogState.show()
        }
    }

    CoreDialog(
        state = dialogState,
        body = {
            TextTitleSmall(text = dialogTitle)
            Spacer(modifier = Modifier.height(16.dp))
            TextBodyMedium(text = dialogMessage, color = SecondDarkGrey)
        },
        selection = CoreSelection(
            negativeButton = null,
            positiveButton = SelectionButton(
                text = "Ok"
            ),
            onPositiveClick = onPositiveButtonDialogClicked
        )
    )

    Scaffold(
        topBar = {
            DetailTopAppBar(
                title = "Register",
                onNavigationClick = navigateUp
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldWithLabel(
                label = "Full Name",
                value = fullName,
                onValueChange = updateFullName,
                supportingText = fullNameErrorMessage,
                isError = fullNameError,
                placeholder = "Enter your name",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldWithLabel(
                label = "Student or lecturer ID Number",
                value = studentIdOrLectureId,
                onValueChange = viewModel::updateStudentIdOrLectureId,
                placeholder = "Enter your ID Number",
                isOptional = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldWithLabel(
                label = "Email",
                value = email,
                onValueChange = updateEmail,
                placeholder = "Enter your email",
                supportingText = emailErrorMessage,
                isError = emailError,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldWithLabel(
                label = "Phone",
                value = phone,
                onValueChange = viewModel::updatePhone,
                placeholder = "Enter your phone number",
                isOptional = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            DropDownTextFieldWithLabel(
                label = "Gender",
                selectedOption = gender,
                options = genders,
                onSelected = { viewModel.updateGender(genders[it]) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            DropDownTextFieldWithLabel(
                label = "Current Status",
                selectedOption = currentStatus,
                options = status,
                onSelected = { viewModel.updateStatus(status[it]) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextFieldWithLabel(
                label = "Password",
                value = password,
                onValueChange = updatePassword,
                placeholder = "Enter your password",
                supportingText = passwordErrorMessage,
                isError = passwordError,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextFieldWithLabel(
                label = "Confirm Password",
                value = confirmPassword,
                onValueChange = updateConfirmPassword,
                placeholder = "Enter your password",
                supportingText = confirmPasswordErrorMessage,
                isError = confirmPasswordError,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            LargePrimaryButton(
                text = "Register",
                onClick = viewModel::register,
                enable = buttonRegisterEnabled,
                loading = buttonRegisterLoading,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextBodySmall(text = "Already have an account? ")
                TextButton(
                    text = "Login",
                    fontSize = 12.sp,
                    onClick = navigateToLoginScreen
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewRegisterScreen() {
    KoinApplication(moduleList = { listOf(authModule) }) {
        ITTPizenTheme {
            Surface {
                RegisterScreen()
            }
        }
    }
}
