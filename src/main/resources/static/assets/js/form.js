const loginForm = document.getElementById('login-form');
const registerForm = document.getElementById('register-form');
const showRegisterLink = document.getElementById('show-register');
const showLoginLink = document.getElementById('show-login');

function showLogin() {
  loginForm.style.display = 'flex';
  registerForm.style.display = 'none';
}

function showRegister() {
  loginForm.style.display = 'none';
  registerForm.style.display = 'flex';
}

if (showRegisterLink) {
  showRegisterLink.addEventListener('click', function(e) {
    e.preventDefault();
    showRegister();
  });
}

if (showLoginLink) {
  showLoginLink.addEventListener('click', function(e) {
    e.preventDefault();
    showLogin();
  });
}

function showError(input, message) {
  const oldError = input.parentElement.querySelector('.error-message');
  if (oldError) {
    oldError.remove();
  }

  const error = document.createElement('span');
  error.className = 'error-message';
  error.textContent = message;

  input.parentElement.insertBefore(error, input.nextSibling);
}

function clearErrors() {
  document.querySelectorAll('.error-message').forEach(el => el.remove());
}

function clearFormFields(form) {
  const inputs = form.querySelectorAll('input');
  inputs.forEach(input => input.value = '');
}

// Login
loginForm.addEventListener('submit', async function(e) {
  e.preventDefault();
  clearErrors();

  const email = document.getElementById('login-email');
  const password = document.getElementById('login-password');

  if (!email.value.trim()) {
    showError(email, 'Email is required.');
    return;
  }

  if (!password.value.trim()) {
    showError(password, 'Password is required.');
    return;
  }

  const submitBtn = this.querySelector('button[type="submit"]');
  submitBtn.disabled = true;
  submitBtn.textContent = 'Logging in...';

  try {
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        email: email.value.trim(),
        password: password.value.trim()
      })
    });

    const data = await response.json();

    if (response.ok && data.success) {
      localStorage.setItem('token', data.token);
      localStorage.setItem('userEmail', data.email);
      localStorage.setItem('userName', data.name);

      window.location.href = '/home.html';
    } else {
      const errorMsg = data.message || 'Invalid email or password';
      showError(password, errorMsg);
      submitBtn.disabled = false;
      submitBtn.textContent = 'Login';
    }

  } catch (error) {
    console.error('Login error:', error);
    showError(password, 'Connection error. Please try again.');
    submitBtn.disabled = false;
    submitBtn.textContent = 'Login';
  }
});

// Register
registerForm.addEventListener('submit', async function(e) {
  e.preventDefault();
  clearErrors();

  const name = document.getElementById('register-name');
  const birthDate = document.getElementById('register-birth');
  const email = document.getElementById('register-email');
  const password = document.getElementById('register-password');

  if (!name.value.trim()) {
    showError(name, 'Full name is required');
    return;
  }

  if (!birthDate.value.trim()) {
    showError(birthDate, 'Birth date is required');
    return;
  }

  if (!email.value.trim()) {
    showError(email, 'Email is required');
    return;
  }

  if (!password.value.trim()) {
    showError(password, 'Password is required');
    return;
  }

  const submitBtn = this.querySelector('button[type="submit"]');
  submitBtn.disabled = true;
  submitBtn.textContent = 'Registering...';

  try {
    const response = await fetch('http://localhost:8080/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: name.value.trim(),
        birthDate: birthDate.value.trim(),
        email: email.value.trim(),
        password: password.value.trim()
      })
    });

    const data = await response.json();

    if (response.ok && data.success) {
      localStorage.setItem('token', data.token);
      localStorage.setItem('userEmail', data.email);
      localStorage.setItem('userName', data.name);

      window.location.href = '/home.html';
    } else {
      const errorMsg = data.message || 'Registration failed. Please try again.';
      showError(email, errorMsg);
      submitBtn.disabled = false;
      submitBtn.textContent = 'Register';
    }

  } catch (error) {
    console.error('Registration error:', error);
    showError(email, 'Connection error. Please try again.');
    submitBtn.disabled = false;
    submitBtn.textContent = 'Register';
  }
});

showLogin();
