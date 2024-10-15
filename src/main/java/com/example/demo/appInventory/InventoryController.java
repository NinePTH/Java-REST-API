package com.example.demo.appInventory;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.algorithm.binSearch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/apps")
public class InventoryController {

    private List<AppInfo> appInfo;
    private int appCount = 0;

    public InventoryController() {
        appInfo = new ArrayList<>();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> getAppInfoById(@PathVariable(name = "id") String id) {
        int targetId = Integer.parseInt(id);
        if (targetId <= appCount) {

            int[] appIds = appInfo.stream().mapToInt(app -> Integer.parseInt(app.getId())).toArray();
            int index = binSearch.search(appIds, targetId);

            if (index != -1) {
                return ResponseEntity.ok(appInfo.get(index));
            }
        }
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("An app with id " + id + " was not found in the inventory");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAllApps() {
        if (appInfo.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(appInfo);
        }
        return ResponseEntity.ok(appInfo);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> addNewApp(@RequestBody AppInfo newApp) {
        appCount += 1;
        newApp.setId(Integer.toString(appCount));

        appInfo.add(newApp);

        return ResponseEntity.status(HttpStatus.CREATED).body(newApp);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> updateApp(@PathVariable(name = "id") String id, @RequestBody AppInfo newAppInfo) {
        int targetId = Integer.parseInt(id);
        if (targetId <= appCount) {

            int[] appIds = appInfo.stream().mapToInt(app -> Integer.parseInt(app.getId())).toArray();
            int index = binSearch.search(appIds, targetId);

            if (index != -1) {
                newAppInfo.setId(id);
                appInfo.set(index, newAppInfo);
                return ResponseEntity.status(HttpStatus.OK).body(newAppInfo);
            }
        }
        System.out.println("ID not found");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("An app with id " + id + " was not found in the inventory");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteApp(@PathVariable(name = "id") String id) {
        int targetId = Integer.parseInt(id);
        if (targetId <= appCount) {

            int[] appIds = appInfo.stream().mapToInt(app -> Integer.parseInt(app.getId())).toArray();
            int index = binSearch.search(appIds, targetId);

            if (index != -1) {
                var removedApp = appInfo.remove(index);
                return ResponseEntity.status(HttpStatus.OK).body(removedApp);
            }
        }
        System.out.println("ID not found");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("An app with id " + id + " was not found in the inventory");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
