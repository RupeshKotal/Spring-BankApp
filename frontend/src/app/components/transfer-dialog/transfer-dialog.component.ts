import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-transfer-dialog',
  templateUrl: './transfer-dialog.component.html',
  styleUrls: ['./transfer-dialog.component.scss']
})
export class TransferDialogComponent {
  transferForm: FormGroup;
  loading = false;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private dialogRef: MatDialogRef<TransferDialogComponent>,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: { account: any }
  ) {
    this.transferForm = this.formBuilder.group({
      toAccountNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      amount: ['', [Validators.required, Validators.min(0.01), Validators.max(this.data.account.balance)]]
    });
  }

  onSubmit(): void {
    if (this.transferForm.invalid) {
      return;
    }

    this.loading = true;
    const transferData = this.transferForm.value;

    this.accountService.transfer(this.data.account.id, transferData).subscribe({
      next: () => {
        this.snackBar.open('Transfer successful!', 'Close', {
          duration: 3000
        });
        this.dialogRef.close(true);
      },
      error: (error) => {
        this.snackBar.open(error.error.message || 'Transfer failed', 'Close', {
          duration: 3000
        });
        this.loading = false;
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
} 