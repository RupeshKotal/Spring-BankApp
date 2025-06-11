import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'type', 'content', 'status', 'createdAt'];
  dataSource: MatTableDataSource<any>;
  loading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private notificationService: NotificationService,
    private snackBar: MatSnackBar
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.loading = true;
    this.notificationService.getNotifications().subscribe({
      next: (notifications) => {
        this.dataSource.data = notifications;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.loading = false;
      },
      error: (error) => {
        this.snackBar.open(error.error.message || 'Error loading notifications', 'Close', {
          duration: 3000
        });
        this.loading = false;
      }
    });
  }

  markAsRead(id: number): void {
    this.notificationService.markAsRead(id).subscribe({
      next: () => {
        this.loadNotifications();
        this.snackBar.open('Notification marked as read', 'Close', {
          duration: 3000
        });
      },
      error: (error) => {
        this.snackBar.open(error.error.message || 'Error marking notification as read', 'Close', {
          duration: 3000
        });
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