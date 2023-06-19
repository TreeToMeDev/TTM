import { Injectable } from "@angular/core";

declare global {
    interface Navigator {
        msSaveBlob?: (blob: any, defaultName?: string) => boolean
    }
}

@Injectable({
    providedIn: 'root'
})
export class CsvDownloadService {
    exportToCsv(filename: string, rows: object[], headers: string[]) {
        if (!rows || !rows.length) {
            return;
        }
        const separator = ',';
        const keys = headers;

        const csvData =
            //  header row
            keys.map( h => {
                //  take camel case header and convert to nicer heading
                //  e.g. firstName => First Name
                const result = h.replace(/([A-Z])/g, " $1");
                const finalResult = result.charAt(0).toUpperCase() + result.slice(1); 
                return finalResult;      
            }).join(separator) + '\n' 
        
            +
        
            //  the rows
            rows.map(row => {
                return keys.map(k => {
                    let cell = row[k] === null || row[k] === undefined ? '' : row[k];
                    cell = cell instanceof Date ? cell.toLocaleString() : cell.toString().replace(/"/g, '""');
                
                    if (cell.search(/("|,|\n)/g) >= 0) {
                        cell = `"${cell}"`;
                    }
                
                    return cell;
                }).join(separator);
            }).join('\n');
  
        const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });
      
        if (navigator.msSaveBlob) { // IE 10+
            navigator.msSaveBlob(blob, filename);
        } else {
            const link = document.createElement('a');
            if (link.download !== undefined) {
                // Browsers that support HTML5 download attribute
                const url = URL.createObjectURL(blob);
                link.setAttribute('href', url);
                link.setAttribute('download', filename);
                link.style.visibility = 'hidden';
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            }
        }
    }
}
  