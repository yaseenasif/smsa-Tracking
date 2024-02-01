import { HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { FileUploadErrorEvent, FileUploadEvent } from 'primeng/fileupload';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-international-summary-by-road-attachments',
  templateUrl: './international-summary-by-road-attachments.component.html',
  styleUrls: ['./international-summary-by-road-attachments.component.scss'],
  providers:[MessageService]
})
export class InternationalSummaryByRoadAttachmentsComponent {
  items: MenuItem[] | undefined;

  constructor(
    private messageService: MessageService,
    private route:ActivatedRoute,
  ) { }
  
  size=environment.fileSize;
  url=environment.baseurl.concat('/add-international-attachments/',this.route.snapshot.paramMap.get('id')!)
  uploadedFiles!:any[];

  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
  });

  onUpload(event:FileUploadEvent,name:string){
   this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: name.concat(' file is uploaded')});
  }
 
  onError(event:FileUploadErrorEvent){
    this.messageService.add({severity: 'error', summary: 'File Uploaded', detail: event.error?.error.body});
  }

  ngOnInit(): void {  
    this.items = [{ label: 'International Inbound By Air',routerLink:'/international-summary-by-air'},{ label: 'Add Attachments'}];
  }
}
