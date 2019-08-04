export class RecoveryQuestion {
  id: string;
  question: string;
  answer: string;
}

export class PasswordReset {
  username: string;
  newPassword: string;
  reNewPassword: string;
}
