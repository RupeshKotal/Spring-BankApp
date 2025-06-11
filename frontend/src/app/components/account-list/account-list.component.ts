import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AccountService } from '../../services/account.service';
import { TransferDialogComponent } from '../transfer-dialog/transfer-dialog.component';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.scss']
})
export class AccountListComponent implements OnInit {
  displayedColumns: string[] = ['accountNumber', 'type', 'balance', 'actions'];
  dataSource: MatTableDataSource<any>;
  loading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private accountService: AccountService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.loading = true;
    this.accountService.getAccounts().subscribe({
      next: (accounts) => {
        this.dataSource.data = accounts;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.loading = false;
      },
      error: (error) => {
        this.snackBar.open(error.error.message || 'Error loading accounts', 'Close', {
          duration: 3000
        });
        this.loading = false;
      }
    });
  }

  openTransferDialog(account: any): void {
    const dialogRef = this.dialog.open(TransferDialogComponent, {
      width: '400px',
      data: { account }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadAccounts();
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
} 